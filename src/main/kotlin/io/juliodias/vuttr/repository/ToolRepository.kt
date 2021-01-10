package io.juliodias.vuttr.repository

import io.juliodias.vuttr.model.Tool
import java.util.UUID
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ToolRepository : JpaRepository<Tool, Long> {

    fun findByUuid(uuid: UUID): Tool?

    fun existsByTitle(title: String): Boolean

    @EntityGraph(attributePaths = ["tags"])
    @Query("SELECT t FROM Tool t INNER JOIN Tag tg ON tg.tool = t.id WHERE tg.name = :tag")
    fun findByTag(tag: String): Set<Tool>
}