package id.elharies.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.elharies.pokedex.data.repository.PokemonRepositoryImpl
import id.elharies.pokedex.data.repository.SessionRepositoryImpl
import id.elharies.pokedex.data.repository.UserRepositoryImpl
import id.elharies.pokedex.domain.repository.PokemonRepository
import id.elharies.pokedex.domain.repository.SessionRepository
import id.elharies.pokedex.domain.repository.UserRepository

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideSessionRepository(sessionRepositoryImpl: SessionRepositoryImpl): SessionRepository

    @Binds
    abstract fun providePokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository

}