package id.elharies.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private var currentOffset = 0
    private var currentQuery: String? = null
    private var searchJob: Job? = null
    private var loadMoreJob: Job? = null

    fun onAction(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GoToDetail -> {}
            HomeIntent.InitData -> loadInitialData()
            is HomeIntent.SearchPoke -> onSearch(intent.name)
            HomeIntent.LoadMore -> loadMoreData()
        }
    }

    private fun loadInitialData() {
        if (_state.value.isLoading) return
        
        currentOffset = 0
        currentQuery = null
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, pokemons = emptyList()) }
            try {
                val pokemons = pokemonRepository.getPokemonList(10, currentOffset, currentQuery)
                _state.update { 
                    it.copy(
                        pokemons = pokemons,
                        isLoading = false,
                        hasReachedEnd = pokemons.size < 10
                    )
                }
                currentOffset += pokemons.size
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Terjadi kesalahan"
                    )
                }
            }
        }
    }

    private fun onSearch(query: String?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            currentOffset = 0
            currentQuery = query
            
            _state.update { it.copy(isLoading = true, errorMessage = null, pokemons = emptyList()) }
            try {
                val pokemons = pokemonRepository.getPokemonList(10, currentOffset, currentQuery)
                _state.update { 
                    it.copy(
                        pokemons = pokemons,
                        isLoading = false,
                        hasReachedEnd = pokemons.size < 10
                    )
                }
                currentOffset += pokemons.size
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Terjadi kesalahan"
                    )
                }
            }
        }
    }

    private fun loadMoreData() {
        if (_state.value.isLoadingMore || _state.value.hasReachedEnd || _state.value.pokemons.isEmpty()) return
        
        loadMoreJob?.cancel()
        loadMoreJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            try {
                val newPokemons = pokemonRepository.getPokemonList(10, currentOffset, currentQuery)
                _state.update { 
                    it.copy(
                        pokemons = it.pokemons + newPokemons,
                        isLoadingMore = false,
                        hasReachedEnd = newPokemons.size < 10
                    )
                }
                currentOffset += newPokemons.size
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingMore = false) }
            }
        }
    }
}