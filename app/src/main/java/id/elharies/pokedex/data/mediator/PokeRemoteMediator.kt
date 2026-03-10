package id.elharies.pokedex.data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.elharies.pokedex.data.local.PokeDatabase
import id.elharies.pokedex.data.local.entity.PokemonEntity
import id.elharies.pokedex.data.local.entity.RemoteKeysEntity
import id.elharies.pokedex.data.mapper.toEntity
import id.elharies.pokedex.data.remote.api.PokeApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokeRemoteMediator(private val api: PokeApi, private val db: PokeDatabase): RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            Log.d("PAGING", "LoadType = $loadType")
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = state.anchorPosition?.let { position ->
                        state.closestItemToPosition(position)?.id?.let { id ->
                            db.remoteKeysDao().getRemoteKeyByPokemonId(id)
                        }
                    }
                    remoteKey?.nextKey?.minus(1) ?: 0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = false)
                    Log.d("PAGING", "Last item = ${lastItem?.id}")
//                    val remoteKey = lastItem?.let {
//                        db.remoteKeysDao().getRemoteKeyByPokemonId(it.id)
//                    }
                    val remoteKey = db.remoteKeysDao().getRemoteKeyByPokemonId(lastItem.id)
                    Log.d("PAGING", "Remote key = $remoteKey")
                    remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)

                }
            }
            val offset = page * state.config.pageSize
            val limit = state.config.pageSize

            val response = api.getPokemonList(limit, offset)
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearAll()
                    db.pokemonDao().clearAll()
                }

                val nextKey = if (response.next == null) null else page + 1
                val prevKey = if (response.previous == null || page == 0) null else page - 1

                val entities = response.results.map { dto ->
                    dto.toEntity(page)
                }
                val keys = entities.map {
                    RemoteKeysEntity(pokemonId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                db.remoteKeysDao().insertAll(keys)
                db.pokemonDao().insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.next == null)
        }  catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}