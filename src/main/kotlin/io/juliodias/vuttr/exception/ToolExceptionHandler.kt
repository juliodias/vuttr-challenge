package io.juliodias.vuttr.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ToolExceptionHandler {

    @ExceptionHandler(ToolNotFoundException::class)
    fun handle(exception: ToolNotFoundException) = ResponseEntity.notFound()

    @ExceptionHandler(ToolAlreadyExistsException::class)
    fun handle(exception: ToolAlreadyExistsException) =
            ResponseEntity.badRequest().body(ErrorResponse(exception.message!!))

    data class ErrorResponse(val message: String)
}