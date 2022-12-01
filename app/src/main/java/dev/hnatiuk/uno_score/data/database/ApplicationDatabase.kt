package dev.hnatiuk.uno_score.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.hnatiuk.uno_score.data.database.dao.PlayerDao
import dev.hnatiuk.uno_score.data.database.entity.PlayerEntity

@Database(
    entities = [PlayerEntity::class],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun providePlayerDao(): PlayerDao
}