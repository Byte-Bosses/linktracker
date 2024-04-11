package ru.bytebosses.scrapper.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.bytebosses.scrapper.domain.entity.ChatEntity

interface ChatRepository : JpaRepository<ChatEntity, Long>
