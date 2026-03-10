package id.elharies.pokedex.data.local.dao.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.elharies.pokedex.data.local.entity.RemoteKeysEntity

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE pokemonId = :pokemonId")
    suspend fun getRemoteKeyByPokemonId(pokemonId: Long): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}