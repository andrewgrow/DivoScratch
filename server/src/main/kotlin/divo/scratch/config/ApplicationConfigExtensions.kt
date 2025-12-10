package divo.scratch.config

import divo.scratch.SERVER.REQUEST_ID_DICTIONARY
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.callid.generate
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.request.path
import io.ktor.server.request.receiveText
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.event.Level
import kotlin.collections.component1
import kotlin.collections.component2

fun Application.configureLoggingAndTracing(
    appConfig: AppConfig,
    log: Logger
) {
    install(CallId) {
        generate(16, REQUEST_ID_DICTIONARY)
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

fun Application.configureJson() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}

