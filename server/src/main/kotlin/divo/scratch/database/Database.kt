package divo.scratch.database

import divo.scratch.config.AppConfig

interface Database

class DatabaseImpl(
    private val appConfig: AppConfig
): Database