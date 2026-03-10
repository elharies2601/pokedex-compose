package id.elharies.pokedex.ui.detail

import androidx.compose.runtime.Stable
import id.elharies.pokedex.domain.model.DetailPokemon

@Stable
data class PokemonDetailUiState(
    val isLoading: Boolean = false,
    val detailPokemon: DetailPokemon = DetailPokemon(),
    val isFound: Boolean = false
)
