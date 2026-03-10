package id.elharies.pokedex.data.repository

import id.elharies.pokedex.data.local.dao.user.UserDao
import id.elharies.pokedex.data.local.entity.UserEntity
import id.elharies.pokedex.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(val userDao: UserDao): UserRepository {
    override suspend fun register(
        userEntity: UserEntity
    ) {
        userDao.registerUser(userEntity)
    }

    override suspend fun login(
        email: String,
        password: String
    ): UserEntity? {
        return userDao.login(email, password)
    }

    override suspend fun getUserById(id: Long): UserEntity? {
        return userDao.getUserById(id)
    }

    override suspend fun isEmailExists(email: String): Boolean {
        return userDao.isEmailExists(email)
    }

}