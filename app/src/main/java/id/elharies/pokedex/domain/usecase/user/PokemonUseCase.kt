package id.elharies.pokedex.domain.usecase.user

import androidx.paging.PagingData
import id.elharies.pokedex.domain.model.DetailPokemon
import id.elharies.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getPokemonList(query: String?): Flow<PagingData<Pokemon>>
    suspend fun getPokemonDetail(id: Long): DetailPokemon
}