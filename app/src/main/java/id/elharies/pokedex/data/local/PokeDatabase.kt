package id.elharies.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.elharies.pokedex.data.local.converters.StatsConverter
import id.elharies.pokedex.data.local.converters.TypesConverter
import id.elharies.pokedex.data.local.dao.pokemon.PokemonDao
import id.elharies.pokedex.data.local.dao.pokemon.PokemonDetailDao
import id.elharies.pokedex.data.local.dao.remotekeys.RemoteKeysDao
import id.elharies.pokedex.data.local.dao.user.UserDao
import id.elharies.pokedex.data.local.entity.PokemonDetailEntity
import id.elharies.pokedex.data.local.entity.PokemonEntity
import id.elharies.pokedex.data.local.entity.RemoteKeysEntity
import id.elharies.pokedex.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PokemonEntity::class,
        PokemonDetailEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1
)
@TypeConverters(
    TypesConverter::class,
    StatsConverter::class
)
abstract class PokeDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun pokemonDetailDao(): PokemonDetailDao
}