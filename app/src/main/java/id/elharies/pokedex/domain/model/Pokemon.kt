package id.elharies.pokedex.domain.model

data class Pokemon(
    val id: Long = 0,
    val pokemonName: String = "",
    val imageUrl: String = "",
    val page: Int = 0
)
