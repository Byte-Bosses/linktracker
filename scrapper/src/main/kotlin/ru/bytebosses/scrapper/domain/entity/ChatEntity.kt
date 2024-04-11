package ru.bytebosses.scrapper.domain.entity

import jakarta.persistence.*
import ru.bytebosses.scrapper.model.Chat

@Entity
@Table(name = "chats")
class ChatEntity (

    @Id
    var id: Long? = null,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "chats_links",
        joinColumns = [JoinColumn(name = "chat_id")],
        inverseJoinColumns = [JoinColumn(name = "link_id")]
    )
    var links: MutableSet<LinkEntity> = mutableSetOf(),
) {

    companion object {
        @JvmStatic
        fun of(chat: Chat) = ChatEntity(chat.id)
    }

    fun toModel() : Chat = Chat(this.id!!)

    fun addLink(link: LinkEntity) {
        link.chats.add(this)
        links.add(link)
    }

    fun removeLink(link: LinkEntity) {
        link.chats.remove(this)
        links.remove(link)
    }
}
