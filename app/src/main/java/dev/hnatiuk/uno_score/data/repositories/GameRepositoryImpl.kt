package dev.hnatiuk.uno_score.data.repositories

import dev.hnatiuk.uno_score.data.database.dao.PlayerDao
import dev.hnatiuk.uno_score.data.source.GameSource
import dev.hnatiuk.uno_score.domain.controller.GameController.getWithNewPlayers
import dev.hnatiuk.uno_score.domain.controller.GameController.getWithRemovedPlayers
import dev.hnatiuk.uno_score.domain.controller.GameController.getWithRoundResult
import dev.hnatiuk.uno_score.domain.controller.GameRoundResult
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameSource: GameSource,
    private val playerDao: PlayerDao
) : GameRepository {

    override fun getGameFlow(gameId: Int): Flow<UnoGame> {
        return gameSource.getGameFlow().filter { game ->
            game.id == gameId
        }
    }

    override suspend fun setGameResult(loserPlayerId: Int) {
        playerDao.incrementPlayerLoseCount(loserPlayerId)
    }

    override suspend fun getGame(gameId: Int): UnoGame? {
        return gameSource.getGame(gameId)
    }

    override suspend fun createGame(finalScore: Int): Int {
        return gameSource.createGame(finalScore)
    }

    override suspend fun setFinalScore(gameId: Int, newFinalScore: Int) {
        gameSource.updateGame(gameId) { game ->
            game.copy(finalScore = newFinalScore)
        }
    }

    override suspend fun setRoundResult(gameId: Int, roundResult: GameRoundResult) {
        gameSource.updateGame(gameId) { game ->
            game.getWithRoundResult(roundResult)
        }
    }

    override suspend fun addPlayers(gameId: Int, vararg newPlayers: Player) {
        gameSource.updateGame(gameId) { game ->
            game.getWithNewPlayers(*newPlayers)
        }
    }

    override suspend fun removePlayers(gameId: Int, vararg ids: Int) {
        gameSource.updateGame(gameId) { game ->
            game.getWithRemovedPlayers(*ids)
        }
    }
//
//    override suspend fun resetGame(gameId: Int) {
//        gameSource.
//    }
}