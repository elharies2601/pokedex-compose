package id.elharies.pokedex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.elharies.pokedex.data.local.PokeDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokeDatabase {
        return Room.databaseBuilder(
            context,
            PokeDatabase::class.java,
            "pokedex.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: PokeDatabase) = database.userDao()

    @Provides
    @Singleton
    fun providePokemonDao(database: PokeDatabase) = database.pokemonDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(database: PokeDatabase) = database.remoteKeysDao()

    @Provides
    @Singleton
    fun providePokemonDetailDao(database: PokeDatabase) = database.pokemonDetailDao()

}