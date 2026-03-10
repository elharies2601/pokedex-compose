package id.elharies.pokedex.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
)  {
    companion object {
        val IS_LOGIN = booleanPreferencesKey("is_login")
        val KEY_USERID = longPreferencesKey("KEY_USERID")
    }

    fun isLogin(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[IS_LOGIN] ?: false
        }
    }

    fun getUserIdLogin(): Flow<Long> {
        return context.dataStore.data.map {
            it[KEY_USERID] ?: 0L
        }
    }

    suspend fun setLogin(isLogin: Boolean = false) {
        context.dataStore.edit {
            it[IS_LOGIN] = isLogin
        }
    }

    suspend fun setUserIdLogin(id: Long) {
        context.dataStore.edit {
            it[KEY_USERID] = id
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit {
            it.clear()
        }
    }
}