package id.elharies.pokedex.domain.usecase.user

import id.elharies.pokedex.domain.model.User

interface UserUseCase {
    suspend fun register(name: String, email: String, password: String): Result<String>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun getUserById(id: Long): Result<User>
    suspend fun getCurrentUserLogin(): Result<User>
    suspend fun logout(): Result<Unit>
}