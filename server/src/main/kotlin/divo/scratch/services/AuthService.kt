package divo.scratch.services

import divo.scratch.database.Database

interface AuthService

class AuthServiceImpl(
    private val database: Database
): AuthService