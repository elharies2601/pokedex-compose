package id.elharies.pokedex.util

object Constants {
    fun getImageUrl(id: Long): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}