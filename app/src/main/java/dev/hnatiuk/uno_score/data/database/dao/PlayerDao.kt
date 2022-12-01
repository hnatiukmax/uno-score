package dev.hnatiuk.uno_score.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.hnatiuk.uno_score.data.database.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Insert
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("DELETE FROM players WHERE id = :id")
    suspend fun removePlayer(id: Int)

    @Query("UPDATE players SET loseCount = (loseCount + 1) WHERE id = :playerId")
    suspend fun incrementPlayerLoseCount(playerId: Int)

    @Query("SELECT * FROM players")
    fun getPlayers(): Flow<List<PlayerEntity>>
}