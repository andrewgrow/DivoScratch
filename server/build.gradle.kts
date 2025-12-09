plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "divo.scratch"
version = "1.0.0"

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}

// running with dotenv
tasks.register<JavaExec>("runWithEnv") {
    group = "application"
    description = "Runs the server with environment variables loaded from server/.env"

    mainClass.set("io.ktor.server.netty.EngineMain")
    classpath = sourceSets["main"].runtimeClasspath

    val envFile = project.file(".env")
    if (!envFile.exists()) {
        throw GradleException("server/.env not found. Please create server/.env based on server/.env.example")
    }

    val envMap: Map<String, String> = envFile.readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() && !it.startsWith("#") }
        .associate { line ->
            val idx = line.indexOf('=')
            require(idx > 0) { "Invalid line in .env: '$line' (expected KEY=VALUE)" }
            val key = line.take(idx).trim()
            val value = line.substring(idx + 1).trim()
            key to value
        }

    environment(envMap)
}