package divo.scratch.controllers

import divo.scratch.services.AuthService
import io.ktor.server.application.ApplicationCall

interface AuthController {
    suspend fun login(call: ApplicationCall) { /* */ }
    suspend fun register(call: ApplicationCall) { /* */ }
}

class AuthControllerImpl(
    private val authService: AuthService
): AuthController {
    override suspend fun login(call: ApplicationCall) {
        /* */
    }

    override suspend fun register(call: ApplicationCall) {
        /* */
    }
}