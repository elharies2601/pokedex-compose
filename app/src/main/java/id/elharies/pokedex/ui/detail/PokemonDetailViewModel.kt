package id.elharies.pokedex.ui.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.base.BaseViewModel
import id.elharies.pokedex.domain.usecase.pokemon.PokemonUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase): BaseViewModel<PokemonDetailUiState>(
    PokemonDetailUiState()
) {
    fun getPokemonDetail(id: Long) {
        reduce {
            copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(500L)
            reduce {
                copy(isLoading = false)
            }

            try {
                val detail = pokemonUseCase.getPokemonDetail(id)
                reduce {
                    copy(detailPokemon = detail, isFound = detail.name.isNotEmpty())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                reduce {
                    copy(isFound = false)
                }
            }
        }
    }
}