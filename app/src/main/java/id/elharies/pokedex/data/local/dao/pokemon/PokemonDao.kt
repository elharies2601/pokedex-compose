package id.elharies.pokedex.data.local.dao.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.elharies.pokedex.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query(
        """
        SELECT * FROM pokemons
        WHERE (:query IS NULL OR name LIKE '%' || :query || '%')
        ORDER BY id ASC
        LIMIT :limit OFFSET :offset
    """
    )
    suspend fun getPokemons(query: String?, limit: Int, offset: Int): List<PokemonEntity>

    @Query(
        """
        SELECT COUNT(*) FROM pokemons
        WHERE (:query IS NULL OR name LIKE '%' || :query || '%')
    """
    )
    suspend fun getPokemonsCount(query: String?): Int

    @Query("DELETE FROM pokemons")
    suspend fun clearAll()
}