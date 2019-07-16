package ru.dmansurov.demo.errors

import org.springframework.http.HttpStatus


open class InternalError(message: String, val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR, cause: Throwable? = null) : Throwable(message, cause)
class NotFoundError(message: String): InternalError(message, HttpStatus.NOT_FOUND)
