package id.elharies.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val pokemonId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
