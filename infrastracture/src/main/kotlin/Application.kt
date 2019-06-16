import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.sample.module.KoinModuleBuilder
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import di.ObjectMapperBuilder
import org.koin.ktor.ext.installKoin
import routes.root

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        // DIコンテナの設定
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

        // routingの設定
        routing {
            root()
        }
    }
    server.start(wait = true)
}