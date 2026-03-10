package id.elharies.pokedex.ui.profile

import androidx.compose.runtime.Stable
import id.elharies.pokedex.domain.model.User

@Stable
data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User = User()
)
