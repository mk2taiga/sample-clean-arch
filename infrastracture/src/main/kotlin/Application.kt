package com.sample

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.util.KtorExperimentalAPI
import mapper.ObjectMapperBuilder
import org.koin.ktor.ext.installKoin

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.main() {
    installKoin(KoinModuleBuilder.modules())

    install(ContentNegotiation) {
        jackson {
            ObjectMapperBuilder.build(this)
            configure(SerializationFeature.INDENT_OUTPUT, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            log.error(cause.message, cause)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
    install(Locations)
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        anyHost()
    }

    routing {
        root()
    }
}

