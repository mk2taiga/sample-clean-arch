package controller

import entity.UserEntity
import usecase.UserUseCase

class UserController(private val useCase: UserUseCase) {
    fun getUser(userId: Long): UserResponse {
        lateinit var response: UserResponse

        useCase.findById(userId) {
            it.either({
                throw IllegalStateException(it)
            }, {
                response = it.toResponse()
            })
        }

        return response
    }
}

data class UserResponse(var userId: Long, var familyName: String, var givenName: String)

private fun UserEntity.toResponse() = UserResponse(id, familyName, givenName)