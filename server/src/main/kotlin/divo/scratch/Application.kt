package divo.scratch

import divo.scratch.config.AppConfig
import divo.scratch.config.configureJson
import divo.scratch.config.configureKoin
import divo.scratch.config.configureLoggingAndTracing
import divo.scratch.config.loadAppConfig
import divo.scratch.routes.authRoutes
import divo.scratch.routes.baseRoutes
import divo.scratch.routes.healthRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.routing.routing
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.module(
    appConfig: AppConfig = loadAppConfig(environment) // override this in tests
) {
    log.info("Starting server with env = ${appConfig.env}")

    install(Koin) {
        slf4jLogger()
        modules(configureKoin(appConfig))
    }

    configureLoggingAndTracing(appConfig, log)
    configureJson()

    routing {
        healthRoutes()
        authRoutes()
        baseRoutes()
    }
}
