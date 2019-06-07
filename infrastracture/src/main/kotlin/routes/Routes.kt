package com.sample.routes

import io.ktor.routing.Routing
import io.ktor.routing.route
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
fun Routing.root() {
    val userController: UserController by inject()

    get<UserParam> { param ->
        call.respond(userController.getUser(param.userId))
    }

    route("v1") {
        route("/users") {
            get<UserParam> { param ->
                call.respond(userController.getUser(param.userId))
            }
        }
    }
}

/**
 * Locations
 */
@KtorExperimentalLocationsAPI
@Location("/{userId}")
data class UserParam(val userId: Long)