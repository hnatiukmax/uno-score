package dev.hnatiuk.uno_score.data.database

import android.content.Context
import androidx.room.Room

object ApplicationDatabaseProvider {

    private const val DATABASE_NAME = "uno_database"

    fun provide(context: Context) = Room.databaseBuilder(
        context,
        ApplicationDatabase::class.java,
        DATABASE_NAME
    ).build()
}