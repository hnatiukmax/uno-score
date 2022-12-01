package dev.hnatiuk.uno_score.data.source;

import dev.hnatiuk.uno_score.domain.controller.GameController
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class GameSourceInMemoryImpl @Inject constructor(): GameSource {

    private val games = mutableMapOf<Int, UnoGame>()
    private val gameFlow = MutableSharedFlow<UnoGame>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun createGame(finalScore: Int): Int {
        val newGame = GameController.createGame(id = games.size + 1, finalScore)
        games[newGame.id] = newGame
        emitGameForId(newGame.id)

        return newGame.id
    }

    override fun getGameFlow(): Flow<UnoGame> {
        return gameFlow
    }

    override suspend fun getGame(gameId: Int): UnoGame? {
        return games[gameId]
    }

    override suspend fun updateGame(id: Int, updateBlock: (oldValue: UnoGame) -> UnoGame) {
        emitForId(id, updateBlock)
    }

    override suspend fun clearGames() {
        games.clear()
    }

    private fun emitGameForId(gameId: Int) {
        val game = games[gameId] ?: return
        gameFlow.tryEmit(game)
    }

    private fun emitForId(gameId: Int, emitBlock: (oldValue: UnoGame) -> UnoGame) {
        val oldGame = games[gameId] ?: return
        val newGame = emitBlock(oldGame)
        games[gameId] = newGame
        gameFlow.tryEmit(newGame)
    }
}