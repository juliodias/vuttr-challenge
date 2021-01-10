package io.juliodias.vuttr.controller

import io.juliodias.vuttr.model.ToolPayload
import io.juliodias.vuttr.service.ToolService
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/tools")
class ToolController(private val toolService: ToolService) {

    @PostMapping
    fun newTool(@RequestBody toolPayload: ToolPayload): ResponseEntity<ToolPayload> {
        val toolResponse = toolService.createTool(toolPayload)
        val toolLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toolResponse.id)
                .toUri()
        return ResponseEntity.created(toolLocation).body(toolResponse)
    }

    @DeleteMapping("{id}")
    fun deleteTool(@PathVariable id: UUID): ResponseEntity<Void> {
        toolService.deleteTool(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun allTools(): ResponseEntity<Set<ToolPayload>> {
        val tools = toolService.getTools()
        return if (tools.isNullOrEmpty()) {
            ResponseEntity.noContent().build()
        } else ResponseEntity.ok(tools.map { it.toToolPayload() }.toCollection(hashSetOf()))
    }

    @GetMapping(params = ["tag"])
    fun toolsByTag(@RequestParam tag: String): ResponseEntity<Set<ToolPayload>> {
        val tools = toolService.getToolsByTag(tag)
        return if (tools.isNullOrEmpty()) {
            ResponseEntity.noContent().build()
        } else ResponseEntity.ok(tools.map { it.toToolPayload() }.toCollection(hashSetOf()))
    }

}