package ru.bytebosses.scrapper.domain.repository

import org.springframework.data.domain.Limit
import org.springframework.data.jpa.repository.JpaRepository
import ru.bytebosses.scrapper.domain.entity.ChatEntity
import ru.bytebosses.scrapper.domain.entity.LinkEntity
import java.time.OffsetDateTime
import java.util.*

interface LinkRepository : JpaRepository<LinkEntity, Long> {

    fun findByUrl(url: String): Optional<LinkEntity>

    fun findAllByLastCheckedAtBeforeOrderByLastCheckedAt(lastCheckedAt: OffsetDateTime, limit: Limit): List<LinkEntity>

    fun findAllByChatsContaining(chat: ChatEntity): List<LinkEntity>
}
