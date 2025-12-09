package divo.scratch.config

import io.ktor.server.application.ApplicationEnvironment

data class AppConfig(
    val env: String
)

fun loadAppConfig(environment: ApplicationEnvironment): AppConfig {
    val appConfig = environment.config.config("app") // server/src/main/resources/application.conf
    return AppConfig(
        env = appConfig.property("env").getString()
    )
}