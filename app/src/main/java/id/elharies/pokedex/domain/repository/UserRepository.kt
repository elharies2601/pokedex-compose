package id.elharies.pokedex.domain.repository

import id.elharies.pokedex.domain.model.User

interface UserRepository {
    suspend fun register(user: User)
    suspend fun login(email: String, password: String): User?
    suspend fun getUserById(id: Long): User?
    suspend fun isEmailExists(email: String): Boolean
}
