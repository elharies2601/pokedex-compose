package id.elharies.pokedex.ui.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.base.BaseViewModel
import id.elharies.pokedex.domain.repository.UserRepository
import id.elharies.pokedex.domain.usecase.user.UserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase): BaseViewModel<ProfileUiState>(
    ProfileUiState()
) {
    private val _event: MutableSharedFlow<ProfileEvent> = MutableSharedFlow()
    val event: SharedFlow<ProfileEvent>
        get() = _event.asSharedFlow()

    fun onAction(intent: ProfileIntent) {
        when(intent) {
            ProfileIntent.GetProfile -> getUserLogin()
            ProfileIntent.Logout -> logout()
        }
    }

    private fun logout() {
        reduce {
            copy(isLoading = true)
        }

        viewModelScope.launch {
            delay(1000L)
            reduce { copy(isLoading = false) }
            userUseCase.logout()
            _event.emit(ProfileEvent.SuccessLogout)
        }
    }

    private fun getUserLogin() {
        reduce {
            copy(isLoading = true)
        }

        viewModelScope.launch {
            delay(1000L)
            reduce {
                copy(isLoading = false)
            }
            userUseCase.getCurrentUserLogin()
                .onSuccess {
                    reduce { copy(user = it) }
                }
                .onFailure {
                    _event.emit(ProfileEvent.ShowToast(it.message.toString()))
                }
        }
    }
}