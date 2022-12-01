package dev.hnatiuk.uno_score.domain.repository

import dev.hnatiuk.uno_score.domain.controller.GameRoundResult
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGameFlow(gameId: Int): Flow<UnoGame>

    suspend fun setGameResult(loserPlayerId: Int)

    suspend fun getGame(gameId: Int): UnoGame?

    suspend fun createGame(finalScore: Int): Int

    suspend fun setFinalScore(gameId: Int, newFinalScore: Int)

    suspend fun setRoundResult(gameId: Int, roundResult: GameRoundResult)

    suspend fun addPlayers(gameId: Int, vararg newPlayers: Player)

    suspend fun removePlayers(gameId: Int, vararg ids: Int)

//    suspend fun resetGame(gameId: Int)
//
//    suspend fun clearGame()
}