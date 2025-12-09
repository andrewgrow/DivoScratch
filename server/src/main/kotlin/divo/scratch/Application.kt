package divo.scratch

import divo.scratch.config.AppConfig
import divo.scratch.config.loadAppConfig
import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.callid.generate
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.request.path
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.slf4j.Logger
import org.slf4j.event.Level

fun Application.module(
    appConfig: AppConfig = loadAppConfig(environment) // override this in tests
) {
    log.info("Starting server with env = ${appConfig.env}")
    configureLoggingAndTracing(appConfig, log)
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}

private fun Application.configureLoggingAndTracing(appConfig: AppConfig, log: Logger) {
    install(CallId) {
        generate(16, "abcdefghijklmnopqrstuvwxyz0123456789")
        header(HttpHeaders.XRequestId)
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            call.request.path() != "/health"
        }
        callIdMdc("callId")
    }

    if (appConfig.env == "development") {
        install(DoubleReceive)
        intercept(ApplicationCallPipeline.Monitoring) {
            val headersDump = call.request.headers
                .entries()
                .joinToString(separator = "; ") { (name, values) ->
                    "$name=${values.joinToString(",")}"
                }

            // print headers
            log.info("Headers: $headersDump")

            // print body
            val body = runCatching { call.receiveText() }.getOrNull()
            if (!body.isNullOrBlank()) {
                val short = if (body.length > 1000) body.take(1000) + "... [truncated]" else body
                log.info("Request Body: $short")
            }
            proceed()
        }
    }
}
