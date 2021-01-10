package io.juliodias.vuttr.exception

import java.lang.RuntimeException

class ToolNotFoundException(message: String) : RuntimeException(message)

class ToolAlreadyExistsException(message: String) : RuntimeException(message)
