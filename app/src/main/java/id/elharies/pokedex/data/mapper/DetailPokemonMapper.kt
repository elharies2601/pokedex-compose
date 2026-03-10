package id.elharies.pokedex.data.mapper

import id.elharies.pokedex.data.local.entity.PokemonDetailEntity
import id.elharies.pokedex.data.remote.dto.DetailPokemonResponse
import id.elharies.pokedex.domain.model.DetailPokemon
import id.elharies.pokedex.util.Constants
import id.elharies.pokedex.util.parseIdFromUrl

fun DetailPokemonResponse.toEntity(): PokemonDetailEntity {
    val imageUrl = Constants.getImageUrl(id)
    return PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        baseExperience = baseExperience,
        weight = weight,
        height = height,
        stats = stats,
        types = types
    )
}

fun PokemonDetailEntity.toDomain(): DetailPokemon {
    return DetailPokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
        baseExperience = baseExperience,
        weight = weight/10.0,
        height = height/10.0,
        stats = stats,
        types = types.map { it.type.name }
    )
}