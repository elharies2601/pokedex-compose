package id.elharies.pokedex.ui.login

sealed interface LoginIntent {
    data class Login(val email: String, val password: String): LoginIntent
    data object CheckIsLoggedIn: LoginIntent
}