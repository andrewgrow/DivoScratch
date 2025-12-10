package divo.scratch.routes

import divo.scratch.api.models.HealthDTO
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Route.healthRoutes() {
    route("/health") {
        get {
            call.respond(
                status = HttpStatusCode.OK,
                message = HealthDTO(status = "OK", timestamp = now().toEpochMilliseconds())
            )
        }
    }
}