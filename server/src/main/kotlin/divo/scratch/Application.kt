package divo.scratch

import divo.scratch.config.AppConfig
import divo.scratch.config.loadAppConfig
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.module(
    appConfig: AppConfig = loadAppConfig(environment) // override this in tests
) {
    log.info("Starting server with env = ${appConfig.env}")

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}