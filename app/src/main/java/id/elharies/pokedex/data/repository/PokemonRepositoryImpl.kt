package id.elharies.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.elharies.pokedex.data.local.PokeDatabase
import id.elharies.pokedex.data.local.entity.PokemonDetailEntity
import id.elharies.pokedex.data.local.entity.PokemonEntity
import id.elharies.pokedex.data.mapper.toEntity
import id.elharies.pokedex.data.mediator.PokeRemoteMediator
import id.elharies.pokedex.data.remote.api.PokeApi
import id.elharies.pokedex.domain.repository.PokemonRepository
import id.elharies.pokedex.util.annotation.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val api: PokeApi, private val db: PokeDatabase, @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher): PokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonList(
        limit: Int,
        query: String?
    ): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(pageSize = limit, prefetchDistance = 5),
            remoteMediator = PokeRemoteMediator(api, db).takeIf {
                query.isNullOrEmpty()
            },
            pagingSourceFactory = {
                db.pokemonDao().getAllPokemons(query)
            }
        ).flow
    }

    override suspend fun getPokemonDetail(name: String): PokemonDetailEntity {
        return withContext(coroutineDispatcher) {
            val detail = db.pokemonDetailDao().getDetailByName(name)
            if (detail == null) {
                val response = api.getPokemonDetail(name)
                val pokemonDetailEntity = response.toEntity()
                db.pokemonDetailDao().insertDetail(pokemonDetailEntity)
                pokemonDetailEntity
            } else {
                detail
            }
        }
    }

    override suspend fun getPokemonDetail(id: Long): PokemonDetailEntity {
        return withContext(coroutineDispatcher) {
            val detail = db.pokemonDetailDao().getDetailById(id)
            if (detail == null) {
                val response = api.getPokemonDetail(id)
                val pokemonDetailEntity = response.toEntity()
                db.pokemonDetailDao().insertDetail(pokemonDetailEntity)
                pokemonDetailEntity
            } else {
                detail
            }
        }
    }
}