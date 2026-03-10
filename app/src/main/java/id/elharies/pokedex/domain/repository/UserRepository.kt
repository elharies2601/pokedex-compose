package id.elharies.pokedex.domain.repository

import id.elharies.pokedex.data.local.entity.UserEntity

interface UserRepository {
    suspend fun register(userEntity: UserEntity)
    suspend fun login(email: String, password: String): UserEntity?
    suspend fun getUserById(id: Long): UserEntity?
    suspend fun isEmailExists(email: String): Boolean
}