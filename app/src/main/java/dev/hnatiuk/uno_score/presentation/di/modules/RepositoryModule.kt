package dev.hnatiuk.uno_score.presentation.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hnatiuk.uno_score.data.repositories.ApplicationSettingsImpl
import dev.hnatiuk.uno_score.data.repositories.GameRepositoryImpl
import dev.hnatiuk.uno_score.data.repositories.PlayerRepositoryImpl
import dev.hnatiuk.uno_score.domain.repository.ApplicationSettings
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import dev.hnatiuk.uno_score.domain.repository.PlayerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGameRepository(repository: GameRepositoryImpl): GameRepository

    @Binds
    @Singleton
    fun bindApplicationSettingsRepository(repository: ApplicationSettingsImpl): ApplicationSettings

    @Binds
    @Singleton
    fun bindPlayersRepository(repository: PlayerRepositoryImpl): PlayerRepository

}