package id.elharies.pokedex.ui.profile

sealed interface ProfileEvent {
    data object SuccessLogout: ProfileEvent
    data class ShowToast(val message: String): ProfileEvent
}