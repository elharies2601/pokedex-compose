package id.elharies.pokedex.data.local.dao.pokemon

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.elharies.pokedex.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun getAllPokemons(): PagingSource<Int, PokemonEntity>

    @Query(
    """
        SELECT * FROM pokemons
        WHERE (:query IS NULL OR name LIKE '%' || :query || '%')
        ORDER BY id ASC
    """
    )
    fun getAllPokemons(query: String?): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemons")
    suspend fun clearAll()
}