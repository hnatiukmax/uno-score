package dev.hnatiuk.uno_score.presentation.di.modules;

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hnatiuk.uno_score.data.database.ApplicationDatabase
import dev.hnatiuk.uno_score.data.database.ApplicationDatabaseProvider
import dev.hnatiuk.uno_score.data.database.dao.PlayerDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        return ApplicationDatabaseProvider.provide(context)
    }

    @Singleton
    @Provides
    fun providePlayerDao(database: ApplicationDatabase): PlayerDao {
        return database.providePlayerDao()
    }
}