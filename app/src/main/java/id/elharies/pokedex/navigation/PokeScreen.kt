package id.elharies.pokedex.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface PokeScreen: NavKey {
    @Serializable
    data object Login: PokeScreen
    @Serializable
    data object Register: PokeScreen
    @Serializable
    data object Main: PokeScreen
    @Serializable
    data object Home: PokeScreen
    @Serializable
    data object Profile: PokeScreen
    @Serializable
    data class Detail(val id: Long): PokeScreen
}