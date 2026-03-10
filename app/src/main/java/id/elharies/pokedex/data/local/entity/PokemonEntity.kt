package id.elharies.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Long = 0,
    val name: String,
    val imageUrl: String,
    val page: Int
)
