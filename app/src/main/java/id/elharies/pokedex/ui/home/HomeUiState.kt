package id.elharies.pokedex.ui.home

import id.elharies.pokedex.domain.model.Pokemon

data class HomeUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasReachedEnd: Boolean = false,
    val errorMessage: String? = null
)
