package id.elharies.pokedex.data.mapper

import id.elharies.pokedex.data.local.entity.UserEntity
import id.elharies.pokedex.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        password = password
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        password = password
    )
}