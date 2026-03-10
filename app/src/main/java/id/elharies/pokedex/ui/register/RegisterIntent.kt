package id.elharies.pokedex.ui.register

sealed interface RegisterIntent {
    data class Register(val nama: String, val email: String, val password: String): RegisterIntent
}