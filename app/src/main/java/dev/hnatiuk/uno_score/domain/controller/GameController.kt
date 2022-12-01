package dev.hnatiuk.uno_score.domain.controller

import dev.hnatiuk.uno_score.domain.entity.GameParticipant
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.RoundResult
import dev.hnatiuk.uno_score.domain.entity.UnoGame

/**
 * map.first = playerId
 * map.second = score for round
 */
typealias GameRoundResult = Map<Int, Int>

object GameController {

    private const val DEFAULT_FIRST_ROUND = 1
    private const val DEFAULT_PLAYER_ROUND_SCORE = 0

    fun createGame(id: Int, finalScore: Int): UnoGame {
        return UnoGame(
            id = id,
            finalScore = finalScore,
            round = DEFAULT_FIRST_ROUND,
            players = emptyList()
        )
    }

    fun UnoGame.getWithRoundResult(roundResult: GameRoundResult): UnoGame {
        val newPlayers = players.map { participant ->
            val playerScore = roundResult[participant.player.id] ?: DEFAULT_PLAYER_ROUND_SCORE
            participant.copy(
                roundResults = participant.roundResults + RoundResult(
                    round = round,
                    result = playerScore
                )
            )
        }

        return this.copy(players = newPlayers)
    }

    fun UnoGame.getWithNewPlayers(vararg playersToAdd: Player): UnoGame {
        val newPlayers = buildList {
            playersToAdd.forEachIndexed { index, player ->
                val playerHasId = player.id != Player.PLAYER_NO_ID
                val playerAlreadyExist = players.any { it.player.id == player.id }

                if (playerHasId.not() || playerAlreadyExist.not()) {
                    val playerId = if (playerHasId) player.id else players.size + index
                    add(GameParticipant(player.copy(id = playerId), emptyList()))
                }
            }
        }

        return this.copy(players = players + newPlayers)
    }

    fun UnoGame.getWithRemovedPlayers(vararg ids: Int): UnoGame {
        val newGamePlayers = players
            .filterNot { ids.contains(it.player.id) }

        return this.copy(players = newGamePlayers)
    }
}