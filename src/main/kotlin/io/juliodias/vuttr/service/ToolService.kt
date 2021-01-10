package io.juliodias.vuttr.service

import io.juliodias.vuttr.exception.ToolAlreadyExistsException
import io.juliodias.vuttr.exception.ToolNotFoundException
import io.juliodias.vuttr.model.Tool
import io.juliodias.vuttr.model.ToolPayload
import io.juliodias.vuttr.repository.ToolRepository
import java.util.UUID
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ToolService(private val toolRepository: ToolRepository) {

    fun getTools(): List<Tool> = toolRepository.findAll()

    fun getToolsByTag(tag: String): Set<Tool> = toolRepository.findByTag(tag)

    @Transactional
    fun deleteTool(id: UUID) {
        val tool = toolRepository.findByUuid(id)?: throw ToolNotFoundException("Tool with UUID: $id not exists!")
        toolRepository.deleteById(tool.id)
    }

    @Transactional
    fun createTool(toolPayload: ToolPayload): ToolPayload {
        val title = toolPayload.title
        if (toolRepository.existsByTitle(title)) {
            throw ToolAlreadyExistsException("Tool with title: $title already exists!")
        }
        val tool = toolPayload.toTool()
        val newTool = toolRepository.save(tool)
        return newTool.toToolPayload()
    }

}