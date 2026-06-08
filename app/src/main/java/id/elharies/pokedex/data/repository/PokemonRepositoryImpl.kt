package id.elharies.pokedex.data.repository

import id.elharies.pokedex.data.local.PokeDatabase
import id.elharies.pokedex.data.mapper.toDomain
import id.elharies.pokedex.data.mapper.toEntity
import id.elharies.pokedex.data.remote.api.PokeApi
import id.elharies.pokedex.domain.exception.NetworkUnavailableException
import id.elharies.pokedex.domain.model.DetailPokemon
import id.elharies.pokedex.domain.model.Pokemon
import id.elharies.pokedex.domain.repository.PokemonRepository
import id.elharies.pokedex.util.annotation.IoDispatcher
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApi,
    private val db: PokeDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int, query: String?): List<Pokemon> {
        return withContext(coroutineDispatcher) {
            val localData = db.pokemonDao().getPokemons(query, limit, offset)
            if (localData.size < limit && query.isNullOrEmpty()) {
                try {
                    val page = offset / limit
                    val response = api.getPokemonList(limit, offset)
                    val entities = response.results.map { it.toEntity(page) }
                    db.pokemonDao().insertAll(entities)
                    db.pokemonDao().getPokemons(query, limit, offset).map { it.toDomain() }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    localData.map { it.toDomain() }
                }
            } else {
                localData.map { it.toDomain() }
            }
        }
    }

    override suspend fun getPokemonDetail(name: String): DetailPokemon {
        return withContext(coroutineDispatcher) {
            val detail = db.pokemonDetailDao().getDetailByName(name)
            detail?.toDomain()
                ?: try {
                    val response = api.getPokemonDetail(name)
                    val pokemonDetailEntity = response.toEntity()
                    db.pokemonDetailDao().insertDetail(pokemonDetailEntity)
                    pokemonDetailEntity.toDomain()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: IOException) {
                    throw NetworkUnavailableException("No internet connection")
                }
        }
    }

    override suspend fun getPokemonDetail(id: Long): DetailPokemon {
        return withContext(coroutineDispatcher) {
            val detail = db.pokemonDetailDao().getDetailById(id)
            detail?.toDomain()
                ?: try {
                    val response = api.getPokemonDetail(id)
                    val pokemonDetailEntity = response.toEntity()
                    db.pokemonDetailDao().insertDetail(pokemonDetailEntity)
                    pokemonDetailEntity.toDomain()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: IOException) {
                    throw NetworkUnavailableException("No internet connection")
                }
        }
    }
}
