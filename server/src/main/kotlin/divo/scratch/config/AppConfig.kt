package divo.scratch.config

import io.ktor.server.application.ApplicationEnvironment

data class DbConfig(
    val url: String,
    val user: String,
    val password: String,
)

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
)

data class AppConfig(
    val env: String,
    val dbConfig: DbConfig,
    val jwtConfig: JwtConfig
)

fun loadAppConfig(environment: ApplicationEnvironment): AppConfig {
    // public config: server/src/main/resources/application.conf
    // secret config (set variables): server/.env
    val appSection = environment.config.config("app")
    val dbSection = appSection.config("db")
    val jwtSection = appSection.config("jwt")

    return AppConfig(
        env = appSection.property("env").getString(),
        dbConfig = DbConfig(
            url = dbSection.property("url").getString(),
            user = dbSection.property("user").getString(),
            password = dbSection.property("password").getString()
        ),
        jwtConfig = JwtConfig(
            secret = jwtSection.property("secret").getString(),
            issuer = jwtSection.property("issuer").getString(),
            audience = jwtSection.property("audience").getString()
        )
    )
}