package dev.hnatiuk.uno_score.presentation.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hnatiuk.uno_score.data.repositories.GameRepositoryImpl
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideGameRepository(repository: GameRepositoryImpl): GameRepository
}