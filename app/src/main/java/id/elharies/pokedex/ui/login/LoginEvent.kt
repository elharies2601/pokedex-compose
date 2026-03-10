package id.elharies.pokedex.ui.login

sealed interface LoginEvent {
    data class ShowToast(val message: String): LoginEvent
    data object NavigateToHome: LoginEvent
    data object None: LoginEvent
}