package ru.bytebosses.scrapper.domain.entity

import jakarta.persistence.*
import ru.bytebosses.scrapper.model.Link
import java.net.URI
import java.time.OffsetDateTime

@Entity
@Table(name = "links")
class LinkEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "url", unique = true, nullable = false)
    var url: String? = null,

    @Column(name = "last_checked_at", nullable = false)
    var lastCheckedAt: OffsetDateTime? = null,

    @Column(name = "last_updated_at", nullable = false)
    var lastUpdatedAt: OffsetDateTime? = null,

    @ElementCollection
    @CollectionTable(
        name = "meta_info",
        joinColumns = [JoinColumn(name = "link_id", referencedColumnName = "id")],
    )
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    var metaInfo: Map<String, String> = mutableMapOf(),

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "links")
    var chats: MutableSet<ChatEntity> = mutableSetOf(),
) {

    companion object {
        fun of(link: Link) = LinkEntity(
            url = link.uri.toString(),
            lastCheckedAt = link.lastCheckTime,
            lastUpdatedAt = link.lastUpdateTime,
            metaInfo = link.metaInfo
        )
    }

    fun toModel() = Link(
        id = id!!,
        uri = URI.create(url!!),
        lastCheckTime = lastCheckedAt!!,
        lastUpdateTime = lastUpdatedAt!!,
        metaInfo = metaInfo
    )
}
