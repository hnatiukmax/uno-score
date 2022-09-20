package dev.hnatiuk.uno_score.data.repositories

import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.RoundResult
import dev.hnatiuk.uno_score.domain.entity.RoundResult.Companion.START_SCORE_ROUND
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor() : GameRepository {

    private val games = mutableMapOf<Int, UnoGame>()
    private val gameFlow = MutableSharedFlow<UnoGame>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun createGame(finalScore: Int, players: List<String>): Int {
        val newGame = UnoGame(
            id = games.size + 1,
            finalScore = finalScore,
            round = DEFAULT_FIRST_ROUND,
            players = players.mapIndexed { index, name ->
                Player(id = index + 1, name = name, roundResults = emptyList())
            }
        )
        games[newGame.id] = newGame
        emitGameForId(newGame.id)

        return newGame.id
    }

    override suspend fun setFinalScore(gameId: Int, score: Int) {
        emitForId(gameId) { game ->
            game.copy(finalScore = score)
        }
    }

    override suspend fun addPlayer(gameId: Int, name: String, startScore: Int?) {
        fun getNewPlayerId(game: UnoGame): Int {
            return (game.players.maxOfOrNull { it.id } ?: 0).plus(1)
        }

        emitForId(gameId) { game ->
            val newPlayer = Player(
                id = getNewPlayerId(game),
                name = name,
                roundResults = if (startScore != null && startScore != 0) listOf(
                    RoundResult(START_SCORE_ROUND, startScore)
                ) else emptyList()
            )
            game.copy(players = game.players + newPlayer)
        }
    }

    override suspend fun removePlayer(gameId: Int, playerId: Int) {
        emitForId(gameId) { game ->
            game.copy(players = game.players.filterNot { player -> player.id == playerId })
        }
    }

    override fun getGameFlow(id: Int): Flow<UnoGame> {
        return gameFlow.filter { game -> game.id == id }
    }

    override suspend fun setRoundResult(gameId: Int, result: Map<Int, Int>) {
        emitForId(gameId) { game ->
            val newPlayers = game.players.map { player ->
                val score = result[player.id] ?: 0
                player.copy(
                    roundResults = player.roundResults + RoundResult(
                        round = game.round,
                        result = score
                    )
                )
            }
            game.copy(players = newPlayers, round = game.round + 1)
        }
    }

    override suspend fun clearGames() {
        games.clear()
    }

    override suspend fun getLastGameId(): Int? {
        return games.values.lastOrNull()?.id
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

    companion object {

        private const val DEFAULT_FIRST_ROUND = 1
    }
}