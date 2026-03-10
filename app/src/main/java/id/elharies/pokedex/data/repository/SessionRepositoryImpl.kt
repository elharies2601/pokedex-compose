package id.elharies.pokedex.data.repository

import id.elharies.pokedex.data.local.datastore.UserDataStore
import id.elharies.pokedex.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(private val dataStore: UserDataStore): SessionRepository {
    override fun isLogin(): Flow<Boolean> {
        return dataStore.isLogin()
    }

    override suspend fun setLogin(isLogin: Boolean) {
        dataStore.setLogin(isLogin)
    }

    override fun getUserIdLogin(): Flow<Long> {
        return dataStore.getUserIdLogin()
    }

    override suspend fun setUserIdLogin(id: Long) {
        dataStore.setUserIdLogin(id)
    }

    override suspend fun clearSession() {
        dataStore.clearSession()
    }
}