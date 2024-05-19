package ru.bytebosses.scrapper.api.common.exception

import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.bytebosses.scrapper.api.common.dto.response.ApiErrorResponse
import java.util.*

/**
 * Handles exceptions and map it to ApiErrorResponse
 */
@RestControllerAdvice
class ApplicationExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleIncorrectRequest(ex, status)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleIncorrectRequest(ex, status)
    }

    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleIncorrectRequest(ex, status)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return handleIncorrectRequest(ex, status)
    }

    private fun handleIncorrectRequest(
        ex: Exception,
        status: HttpStatusCode
    ): ResponseEntity<Any> {
        return ResponseEntity<Any>(
            ApiErrorResponse(
                "Некорректные параметры запроса",
                status.value().toString(),
                ex.javaClass.simpleName,
                ex.message!!,
                Arrays.stream(ex.stackTrace).map { obj: StackTraceElement -> obj.toString() }
                    .toList()
            ),
            status
        )
    }

    @ExceptionHandler(ScrapperException::class)
    fun handleScrapperException(ex: ScrapperException): ResponseEntity<ApiErrorResponse?> {
        return ResponseEntity(
            ApiErrorResponse(
                ex.message,
                "${ex.code.value()}",
                ex.javaClass.getSimpleName(),
                ex.message,
                Arrays.stream(ex.stackTrace).map(StackTraceElement::toString).toList()
            ),
            ex.code
        )
    }
}
