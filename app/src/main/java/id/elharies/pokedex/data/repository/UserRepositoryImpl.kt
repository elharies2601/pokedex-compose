package id.elharies.pokedex.data.repository

import id.elharies.pokedex.data.local.dao.user.UserDao
import id.elharies.pokedex.data.local.entity.UserEntity
import id.elharies.pokedex.data.mapper.toDomain
import id.elharies.pokedex.domain.model.User
import id.elharies.pokedex.domain.repository.UserRepository
import id.elharies.pokedex.util.annotation.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun register(user: User) = withContext(ioDispatcher) {
        userDao.registerUser(
            UserEntity(
                name = user.name,
                email = user.email,
                password = user.password
            )
        )
    }

    override suspend fun login(email: String, password: String): User? = withContext(ioDispatcher) {
        userDao.login(email, password)?.toDomain()
    }

    override suspend fun getUserById(id: Long): User? = withContext(ioDispatcher) {
        userDao.getUserById(id)?.toDomain()
    }

    override suspend fun isEmailExists(email: String): Boolean = withContext(ioDispatcher) {
        userDao.isEmailExists(email)
    }
}