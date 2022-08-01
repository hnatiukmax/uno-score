package dev.hnatiuk.uno_score.presentation.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hnatiuk.uno_score.data.repositories.SettingsRepositoryImpl
import dev.hnatiuk.uno_score.domain.repository.SettingsRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository
}