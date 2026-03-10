package id.elharies.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.elharies.pokedex.data.remote.dto.StatsResponse
import id.elharies.pokedex.data.remote.dto.TypeResponse

@Entity("pokemon_detail")
data class PokemonDetailEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String,
    val baseExperience: Int,
    val weight: Double,
    val height: Double,
    val stats: List<StatsResponse>,
    val types: List<TypeResponse>
)
