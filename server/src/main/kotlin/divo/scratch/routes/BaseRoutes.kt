package divo.scratch.routes

import divo.scratch.Greeting
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.baseRoutes() {
    route("/") {
        get {
            call.respond(
                status = HttpStatusCode.OK,
                message = "Ktor: ${Greeting().greet()}"
            )
        }
    }
}