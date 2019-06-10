package repository

import dto.UserDto
import function.Either

interface UserRepository {
    fun findById(userId: Long): UserDto
}