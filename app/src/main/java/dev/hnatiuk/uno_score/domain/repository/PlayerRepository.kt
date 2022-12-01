package dev.hnatiuk.uno_score.domain.repository

import dev.hnatiuk.uno_score.domain.entity.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun addPlayer(name: String)

    suspend fun removePlayer(id: Int)

    suspend fun setPlayerLoseCount(playerId: Int, loseCount: Int)

    fun getPlayers(): Flow<List<Player>>
}