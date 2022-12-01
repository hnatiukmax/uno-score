package dev.hnatiuk.uno_score.presentation.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hnatiuk.uno_score.data.source.GameSource
import dev.hnatiuk.uno_score.data.source.GameSourceInMemoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {

    @Binds
    @Singleton
    fun bindGameSourceInMemoryImpl(source: GameSourceInMemoryImpl): GameSource
}