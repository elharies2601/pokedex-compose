package id.elharies.pokedex.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun isLogin(): Flow<Boolean>
    suspend fun setLogin(isLogin: Boolean = false)
    fun getUserIdLogin(): Flow<Long>
    suspend fun setUserIdLogin(id: Long)
    suspend fun clearSession()
}