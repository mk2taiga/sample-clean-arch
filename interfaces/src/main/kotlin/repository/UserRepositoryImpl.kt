package repository

import dto.UserDto
import function.Either

class UserRepositoryImpl: UserRepository {
    override fun findById(userId: Long): UserDto {
        return UserDto(1, "Zakimiii", "Tensai")
    }
}