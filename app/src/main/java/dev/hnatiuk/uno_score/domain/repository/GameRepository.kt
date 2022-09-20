package dev.hnatiuk.uno_score.domain.repository

import dev.hnatiuk.uno_score.domain.entity.UnoGame
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun createGame(finalScore: Int, players: List<String> = emptyList()): Int

    suspend fun setFinalScore(gameId: Int, score: Int)

    suspend fun addPlayer(gameId: Int, name: String, startScore: Int? = null)

    suspend fun removePlayer(gameId: Int, playerId: Int)

    suspend fun setRoundResult(gameId: Int, result: Map<Int, Int>)

    suspend fun getLastGameId(): Int?

    suspend fun clearGames()

    fun getGameFlow(id: Int): Flow<UnoGame>
}