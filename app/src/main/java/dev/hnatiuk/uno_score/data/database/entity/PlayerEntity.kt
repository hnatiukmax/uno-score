package dev.hnatiuk.uno_score.data.database.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "players")
data class PlayerEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "loseCount") val loseCount: Int
) : IdEntity()