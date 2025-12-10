package divo.scratch.config

import divo.scratch.controllers.AuthController
import divo.scratch.controllers.AuthControllerImpl
import divo.scratch.database.Database
import divo.scratch.database.DatabaseImpl
import divo.scratch.services.AuthService
import divo.scratch.services.AuthServiceImpl
import org.koin.dsl.module

fun configureKoin(appConfig: AppConfig) = module {
    single<AppConfig> { appConfig }
    single<Database> { DatabaseImpl(get()) }
    factory<AuthService> { AuthServiceImpl(get()) }
    factory<AuthController> { AuthControllerImpl(get()) }
}