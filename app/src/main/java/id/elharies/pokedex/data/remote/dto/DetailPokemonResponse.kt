package id.elharies.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailPokemonResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("height")
    val height: Double,
    @SerializedName("stats")
    val stats: List<StatsResponse> = listOf(),
    @SerializedName("types")
    val types: List<TypeResponse> = listOf()
)
