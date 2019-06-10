package com.sample.module

import controller.UserController
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import repository.UserRepository
import repository.UserRepositoryImpl
import usecase.UserUseCase
import usecase.UserUseCaseImpl

object KoinModuleBuilder {
    fun modules(): List<Module> = listOf(module {
        // Controllers
        single { UserController(get()) }

        // Services
        single<UserUseCase> { UserUseCaseImpl(get()) }

        // Repositories
        single<UserRepository> { UserRepositoryImpl() }
    })
}