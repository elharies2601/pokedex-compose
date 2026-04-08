package id.elharies.pokedex.domain.usecase.pokemon

import androidx.paging.PagingData
import androidx.paging.map
import id.elharies.pokedex.data.mapper.toDomain
import id.elharies.pokedex.domain.model.DetailPokemon
import id.elharies.pokedex.domain.model.Pokemon
import id.elharies.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val repository: PokemonRepository) : PokemonUseCase  {
    override fun getPokemonList(
        query: String?
    ): Flow<PagingData<Pokemon>> {
        return repository.getPokemonList(10,query).map {
            it.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun getPokemonDetail(id: Long): DetailPokemon {
        return repository.getPokemonDetail(id).toDomain()
    }
}