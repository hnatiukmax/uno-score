package dev.hnatiuk.uno_score.presentation

import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GameInteractor @Inject constructor(
    private val gameRepository: GameRepository
) {

    private var _finalScore: Int? = null
    private var _currentGameId: Int? = null
    private val gameId get() = _currentGameId!!

    fun getGameFlow(): Flow<UnoGame> {
        return gameRepository.getGameFlow(gameId)
            .distinctUntilChanged()
    }

    suspend fun resetGame() {
        gameRepository
            .getGame(gameId)
            ?.players
            ?.maxBy { it.score }
            ?.let { gameRepository.setGameResult(it.player.id) }

        _finalScore?.let { startGame(it) }
    }

    suspend fun setFinalScore(newFinalScore: Int) {
        gameRepository.setFinalScore(gameId, newFinalScore)
    }

    suspend fun removePlayers(vararg ids: Int) {
        gameRepository.removePlayers(gameId, *ids)
    }

    suspend fun startGame(finalScore: Int) {
        _currentGameId = gameRepository.createGame(finalScore)
    }

    suspend fun addNewPlayer(name: String) {
        gameRepository.addPlayers(gameId, Player.createNew(name))
    }
}