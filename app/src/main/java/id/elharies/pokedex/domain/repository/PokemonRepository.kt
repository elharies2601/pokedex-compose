package id.elharies.pokedex.domain.repository

import androidx.paging.PagingData
import id.elharies.pokedex.data.local.entity.PokemonDetailEntity
import id.elharies.pokedex.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(limit: Int, query: String?): Flow<PagingData<PokemonEntity>>
    suspend fun getPokemonDetail(name: String): PokemonDetailEntity
    suspend fun getPokemonDetail(id: Long): PokemonDetailEntity
}