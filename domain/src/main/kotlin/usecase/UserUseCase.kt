package usecase

import dto.UserDto
import entity.UserEntity
import function.Either
import repository.UserRepository

interface UserUseCase {
    fun findById(userId: Long, onResult: (Either<String, UserEntity>) -> Unit)
}

class UserUseCaseImpl(
        private val repository: UserRepository
) : UserUseCase {
    override fun findById(userId: Long, onResult: (Either<String, UserEntity>) -> Unit) {
        val res = repository.findById(userId)
        return res.let {
            onResult(Either.Right(res.toEntity()))
        } ?: onResult(Either.Left("No User Found for Given Id"))
    }

    private fun UserDto.toEntity() = UserEntity(id, familyName, givenName)
}