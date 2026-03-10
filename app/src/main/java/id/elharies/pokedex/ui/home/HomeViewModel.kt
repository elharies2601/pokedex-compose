package id.elharies.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.elharies.pokedex.domain.usecase.user.PokemonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val searchQuery = MutableStateFlow<String?>(null)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val dataPokemons = searchQuery
        .debounce(500L)
        .flatMapLatest {
            pokemonUseCase.getPokemonList(it)
        }.cachedIn(viewModelScope)

    fun onAction(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GoToDetail -> {}
            HomeIntent.InitData -> onSearch(null)
            is HomeIntent.SearchPoke -> onSearch(intent.name)
        }
    }

    private fun onSearch(query: String?) {
        searchQuery.value = query
    }
}