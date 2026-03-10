package id.elharies.pokedex.data.local.dao.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.elharies.pokedex.data.local.entity.PokemonDetailEntity

@Dao
interface PokemonDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(detail: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon_detail WHERE name = :name LIMIT 1")
    suspend fun getDetailByName(name: String): PokemonDetailEntity?

    @Query("SELECT * FROM pokemon_detail WHERE id = :id LIMIT 1")
    suspend fun getDetailById(id: Long): PokemonDetailEntity?
}