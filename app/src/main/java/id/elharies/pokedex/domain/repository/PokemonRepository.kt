package id.elharies.pokedex.domain.repository

import id.elharies.pokedex.domain.model.DetailPokemon
import id.elharies.pokedex.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int, query: String?): List<Pokemon>
    suspend fun getPokemonDetail(name: String): DetailPokemon
    suspend fun getPokemonDetail(id: Long): DetailPokemon
}
