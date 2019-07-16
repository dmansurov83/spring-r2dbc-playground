package ru.dmansurov.demo.errors

import org.springframework.boot.autoconfigure.web.ResourceProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import java.util.*

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(errorAttributes: ErrorAttributes?, resourceProperties: ResourceProperties?, applicationContext: ApplicationContext?,
                                     configurer: ServerCodecConfigurer) : AbstractErrorWebExceptionHandler(errorAttributes, resourceProperties, applicationContext) {

    init {
        this.setMessageWriters(configurer.writers)
    }

    override fun getRoutingFunction(
            errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {

        return RouterFunctions.route(
                RequestPredicates.all(), HandlerFunction { this.renderErrorResponse(it) })
    }

    private fun renderErrorResponse(
            request: ServerRequest): Mono<ServerResponse> {

        val error = getError(request).let {
            if (it is InternalError) it else InternalError(message = it.message ?: "Internal error", cause = it)
        }
        val errorPropertiesMap = getErrorAttributes(request, false)

        return ServerResponse.status(error.status)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(mapOf("error" to error.toView(request))))
    }
}

data class InternalErrorView(val status: HttpStatus, val message: String, val path: String, val timeStamp: Date = Date())

fun InternalError.toView(req: ServerRequest) = InternalErrorView(status, message ?: "", req.path())
