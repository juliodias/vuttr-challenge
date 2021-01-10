package io.juliodias.vuttr.model

import java.util.UUID

data class TagPayload(val name: String) {

    fun toTag() = Tag(name)
}

data class ToolPayload(
        val id: UUID?,
        val link: String,
        val title: String,
        val tags: Set<String>,
        val description: String
) {

    fun toTool(): Tool {
        val tool = Tool(link, title, description)
        tags.forEach { tool.addTag(Tag(it)) }
        return tool
    }
}