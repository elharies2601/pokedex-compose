package id.elharies.pokedex.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.base.BaseViewModel
import id.elharies.pokedex.data.local.datastore.UserDataStore
import id.elharies.pokedex.domain.repository.SessionRepository
import id.elharies.pokedex.domain.repository.UserRepository
import id.elharies.pokedex.domain.usecase.user.UserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase, private val sessionRepository: SessionRepository): BaseViewModel<LoginUiState>(
    LoginUiState()
) {
    private val _event = MutableSharedFlow<LoginEvent>(extraBufferCapacity = 1)
    val event: SharedFlow<LoginEvent>
        get() = _event.asSharedFlow()

    fun onAction(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> login(intent.email, intent.password)
            is LoginIntent.CheckIsLoggedIn -> checkIsLoggedIn()
        }
    }

    private fun checkIsLoggedIn() {
        reduce { copy(isLoading = true) }
        viewModelScope.launch {
            sessionRepository.isLogin().collect {
                reduce {
                    copy(isLoading = false, alreadyLogin = it)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        reduce {
            copy(isLoading = true)
        }

        viewModelScope.launch {
            delay(1000L)
            reduce {
                copy(isLoading = false)
            }
            userUseCase.login(email, password).onSuccess {
                _event.emit(LoginEvent.NavigateToHome)
            }.onFailure {
                _event.emit(LoginEvent.ShowToast(it.message.orEmpty()))
            }
        }
    }
}