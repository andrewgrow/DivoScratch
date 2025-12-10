package divo.scratch.routes

import divo.scratch.controllers.AuthController
import io.ktor.server.application.ApplicationCall
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.plugin.scope

fun Route.authRoutes(authController: AuthController? = null) {

    /** Get from constructor or injected by koin */
    fun resolveController(call: ApplicationCall): AuthController {
        return authController ?: call.scope.get<AuthController>()
    }

    route("/auth") {
        post("/login") {
            resolveController(call).login(call)
        }
        post("/register") {
            resolveController(call).register(call)
        }
    }
}