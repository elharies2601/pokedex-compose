package id.elharies.pokedex.domain.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import id.elharies.pokedex.data.remote.dto.StatsResponse
import id.elharies.pokedex.data.remote.dto.TypeResponse
import id.elharies.pokedex.util.ColorPokemon
import kotlin.random.Random

data class DetailPokemon(
    val id: Long = 0,
    val name: String = "",
    val imageUrl: String = "",
    val baseExperience: Int = 0,
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val stats: List<StatsResponse> = listOf(),
    val types: List<String> = listOf()
) {
    val hp: Int by lazy {
        stats.firstOrNull { it.stat.name == "hp" }?.baseStat ?: Random.nextInt(200)
    }
    val attack: Int by lazy {
        stats.firstOrNull { it.stat.name == "attack" }?.baseStat ?: Random.nextInt(200)
    }
    val defense: Int by lazy {
        stats.firstOrNull { it.stat.name == "defense" }?.baseStat ?: Random.nextInt(200)
    }
    val speed: Int by lazy {
        stats.firstOrNull { it.stat.name == "speed" }?.baseStat ?: Random.nextInt(200)
    }
    val backgroundColor: Color by lazy {
        val type = types.firstOrNull() ?: "unknown"
        ColorPokemon.entries.find { it.typeName == type }?.color ?: Color.White
    }
}
