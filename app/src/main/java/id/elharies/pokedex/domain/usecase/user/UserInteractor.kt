package id.elharies.pokedex.domain.usecase.user

import id.elharies.pokedex.data.local.entity.UserEntity
import id.elharies.pokedex.data.mapper.toDomain
import id.elharies.pokedex.domain.model.User
import id.elharies.pokedex.domain.repository.SessionRepository
import id.elharies.pokedex.domain.repository.UserRepository
import id.elharies.pokedex.util.hashPassword
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository, private val sessionRepository: SessionRepository) : UserUseCase  {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<String> {
        val isEmailExists = userRepository.isEmailExists(email.lowercase())
        if (isEmailExists) {
            return Result.failure(Exception("Email sudah terdaftar, silahkan login menggunakan akunmu"))
        }

        try {
            userRepository.register(UserEntity(name = name, email = email.lowercase(), password = password.hashPassword()))
            return Result.success("Berhasil mendaftarkan akun")
        } catch (e: Exception) {
            return Result.failure(Exception("Gagal mendaftarkan akun\n${e.message}"))
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        val user = userRepository.login(email.lowercase(), password.hashPassword())
            ?: return Result.failure(Exception("Email atau password salah"))
        return try {
            sessionRepository.setLogin(true)
            sessionRepository.setUserIdLogin(user.id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Gagal login\n${e.message}"))
        }
    }

    override suspend fun getUserById(id: Long): Result<User> {
        val userEntity = userRepository.getUserById(id)
            ?: return Result.failure(Exception("User tidak ditemukan"))
        return try {
            Result.success(userEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(Exception("User tidak ditemukan\n${e.message}"))
        }
    }

    override suspend fun getCurrentUserLogin(): Result<User> {
        val userId = sessionRepository.getUserIdLogin().first()
        return getUserById(userId)
    }

    override suspend fun logout(): Result<Unit> {
        sessionRepository.clearSession()
        return Result.success(Unit)
    }
}