package id.elharies.pokedex.ui.profile

sealed interface ProfileIntent {
    data object GetProfile: ProfileIntent
    data object Logout: ProfileIntent
}