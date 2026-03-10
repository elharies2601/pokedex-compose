package id.elharies.pokedex.ui.register

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.base.BaseViewModel
import id.elharies.pokedex.domain.usecase.user.UserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    BaseViewModel<RegisterUiState>(RegisterUiState()) {

    private val _event: MutableSharedFlow<RegisterEvent> = MutableSharedFlow(extraBufferCapacity = 1)
    val event: SharedFlow<RegisterEvent>
        get() = _event.asSharedFlow()

    fun onAction(intent: RegisterIntent) {
        when(intent) {
            is RegisterIntent.Register -> register(intent.nama, intent.email, intent.password)
        }
    }

    private fun register(name: String, email: String, password: String) {
        reduce {
            copy(isLoading = true)
        }

        viewModelScope.launch {
            delay(1000L)
            reduce {
                copy(isLoading = false)
            }
            userUseCase.register(name, email, password).onSuccess {
                _event.emit(RegisterEvent.Success(it))
            }.onFailure {
                _event.emit(RegisterEvent.ShowMessage(it.message.orEmpty()))
            }
        }

    }
}