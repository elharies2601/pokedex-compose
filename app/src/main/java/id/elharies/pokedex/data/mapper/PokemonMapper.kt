package id.elharies.pokedex.data.mapper

import id.elharies.pokedex.data.local.entity.PokemonEntity
import id.elharies.pokedex.data.remote.dto.PokemonResponse
import id.elharies.pokedex.domain.model.Pokemon
import id.elharies.pokedex.util.Constants
import id.elharies.pokedex.util.parseIdFromUrl

fun PokemonResponse.PokemonItem.toEntity(page: Int): PokemonEntity {
    val id = this.url.parseIdFromUrl()
    val imageUrl = Constants.getImageUrl(id)
    return PokemonEntity(
        id = id,
        name = this.name,
        imageUrl = imageUrl,
        page = page
    )
}

fun PokemonEntity.toDomain() = Pokemon(
    id = this.id,
    pokemonName = this.name,
    imageUrl = this.imageUrl,
    page = this.page
)

fun List<PokemonEntity>.toDomain() = this.map { it.toDomain() }