package ru.bytebosses.scrapper.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.bytebosses.scrapper.domain.entity.LinkEntity
import java.time.OffsetDateTime
import java.util.*

interface LinkRepository : JpaRepository<LinkEntity, Int> {

    fun findByUrl(url: String): Optional<LinkEntity>

    fun findAllByLastCheckedAtOrderByLastCheckedAt(lastCheckedAt: OffsetDateTime): List<LinkEntity>
}
