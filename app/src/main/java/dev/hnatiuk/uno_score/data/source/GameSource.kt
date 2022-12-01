package dev.hnatiuk.uno_score.data.source

import dev.hnatiuk.uno_score.domain.entity.UnoGame
import kotlinx.coroutines.flow.Flow

interface GameSource {

    fun getGameFlow(): Flow<UnoGame>

    suspend fun getGame(gameId: Int): UnoGame?

    suspend fun createGame(finalScore: Int): Int

    suspend fun updateGame(id: Int, updateBlock: (oldValue: UnoGame) -> UnoGame)

    suspend fun clearGames()
}