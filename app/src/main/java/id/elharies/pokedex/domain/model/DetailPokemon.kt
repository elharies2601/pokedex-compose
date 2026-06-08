package id.elharies.pokedex.domain.model

data class DetailPokemon(
    val id: Long = 0,
    val name: String = "",
    val imageUrl: String = "",
    val baseExperience: Int = 0,
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val stats: List<PokemonStat> = emptyList(),
    val types: List<String> = emptyList()
) {
    val hp: Int get() = stats.firstOrNull { it.name == "hp" }?.baseStat ?: 0
    val attack: Int get() = stats.firstOrNull { it.name == "attack" }?.baseStat ?: 0
    val defense: Int get() = stats.firstOrNull { it.name == "defense" }?.baseStat ?: 0
    val speed: Int get() = stats.firstOrNull { it.name == "speed" }?.baseStat ?: 0
}
