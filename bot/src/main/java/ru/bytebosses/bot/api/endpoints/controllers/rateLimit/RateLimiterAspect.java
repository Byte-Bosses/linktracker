package ru.bytebosses.bot.api.endpoints.controllers.rateLimit;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.bytebosses.bot.api.exceptions.TooManyRequestsException;

@Aspect
@Validated
@ConfigurationProperties(prefix = "rate-limiting")
public class RateLimiterAspect {
    private final Integer capacity;
    private final Integer tokensPerPeriod;
    private final Duration genPeriod;
    private final Set<String> excludedIPs;
    private final Map<String, RateLimiter> bucketMap;

    public RateLimiterAspect(
        Integer capacity,
        Integer tokensPerPeriod,
        Duration genPeriod,
        Set<String> excludedIPs
    ) {
        this.bucketMap = new ConcurrentHashMap<>();
        this.excludedIPs = excludedIPs;
        this.capacity = capacity;
        this.genPeriod = genPeriod;
        this.tokensPerPeriod = tokensPerPeriod;
    }

    /**
     * Rate limit check based on IP address for the given joinPoint and consumes token if could.
     *
     * @param  joinPoint  the join point for the advice
     * @throws TooManyRequestsException if limit is exceeded
     */
    @Before("@annotation(RateLimit)")
    public void rateLimitCheck(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String ipAddress = null;
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest request) {
                ipAddress = request.getRemoteAddr();
                break;
            }
        }
        if (ipAddress == null) {
            return;
        }
        bucketMap.putIfAbsent(ipAddress, new RateLimiter(capacity, tokensPerPeriod, genPeriod));
        RateLimiter limiter = bucketMap.get(ipAddress);
        if (!excludedIPs.contains(ipAddress) && !limiter.tryConsumeToken()) {
            throw new TooManyRequestsException();
        }
    }
}

