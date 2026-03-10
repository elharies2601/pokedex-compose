package id.elharies.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.elharies.pokedex.domain.usecase.user.PokemonInteractor
import id.elharies.pokedex.domain.usecase.user.PokemonUseCase
import id.elharies.pokedex.domain.usecase.user.UserInteractor
import id.elharies.pokedex.domain.usecase.user.UserUseCase

@Module(includes = [RepositoryModule::class])
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideUserUseCase(userUseCase: UserInteractor): UserUseCase

    @Binds
    abstract fun providePokemonUseCase(pokemonUseCase: PokemonInteractor): PokemonUseCase
}