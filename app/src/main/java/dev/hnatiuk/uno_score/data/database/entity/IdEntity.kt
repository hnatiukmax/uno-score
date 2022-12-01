package dev.hnatiuk.uno_score.data.database.entity;

import androidx.room.PrimaryKey

open class IdEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}