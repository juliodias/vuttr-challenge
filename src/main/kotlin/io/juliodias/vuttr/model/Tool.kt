package io.juliodias.vuttr.model

import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tools")
data class Tool(
        @Column(nullable = false)
        val link: String,

        @Column(nullable = false)
        val title: String,

        @Column(nullable = false)
        val description: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    val uuid: UUID = UUID.randomUUID()

    @OneToMany(
            mappedBy = "tool",
            orphanRemoval = true,
            cascade = [CascadeType.ALL]
    )
    val tags: MutableSet<Tag> = mutableSetOf()

    fun addTag(tag: Tag) {
        tags.add(tag)
        tag.tool = this
    }

    fun toToolPayload(): ToolPayload =
            ToolPayload(uuid, link, title, tags.map { it.name }.toCollection(mutableSetOf()), description)

}