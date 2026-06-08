package id.elharies.pokedex.ui.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.base.BaseViewModel
import id.elharies.pokedex.domain.exception.NetworkUnavailableException
import id.elharies.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonRepository: PokemonRepository): BaseViewModel<PokemonDetailUiState>(
    PokemonDetailUiState()
) {
    fun getPokemonDetail(id: Long) {
        reduce {
            copy(isLoading = true, errorType = null, errorMessage = null)
        }
        viewModelScope.launch {
            delay(500L)
            reduce {
                copy(isLoading = false)
            }

            try {
                val detail = pokemonRepository.getPokemonDetail(id)
                reduce {
                    copy(detailPokemon = detail)
                }
            } catch (e: NetworkUnavailableException) {
                reduce {
                    copy(
                        errorType = DetailErrorType.NoConnection,
                        errorMessage = "Data Pokemon ini belum tersedia secara offline. Pastikan perangkat terhubung ke internet."
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                reduce {
                    copy(
                        errorType = DetailErrorType.Unknown,
                        errorMessage = e.message ?: "Terjadi kesalahan saat memuat data Pokemon"
                    )
                }
            }
        }
    }

    fun retry(id: Long) {
        reduce {
            copy(errorType = null, errorMessage = null)
        }
        getPokemonDetail(id)
    }
}