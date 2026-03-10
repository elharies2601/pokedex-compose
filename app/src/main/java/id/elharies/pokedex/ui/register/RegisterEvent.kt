package id.elharies.pokedex.ui.register

sealed interface RegisterEvent {
    data class ShowMessage(val message: String): RegisterEvent
    data class Success(val message: String): RegisterEvent
    data object None: RegisterEvent
}