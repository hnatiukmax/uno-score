package dev.hnatiuk.uno_score.presentation.recyclerview.mappers

import androidx.annotation.ColorRes
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.RoundResult
import dev.hnatiuk.uno_score.domain.entity.RoundResult.Companion.START_SCORE_ROUND
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem

object PlayerItemMapper {

    fun mapPlayersToPlayerItems(finalScore: Int, list: List<Player>): List<PlayerItem> {
        return list.mapIndexed { playerIndex, player ->
            PlayerItem.Player(
                id = player.id,
                number = playerIndex + 1,
                name = player.name,
                roundHistory = player.roundResults.map { result ->
                    PlayerItem.Player.RoundResult(
                        round = result.round,
                        score = result.result,
                        color = calculateColorForRoundScore(finalScore, result)
                    )
                },
                score = player.score,
                scoreBackgroundColor = calculateColorForCurrentScore(finalScore, player.score)
            )
        }
            .sortedByDescending { it.score }
            .mapIndexed { index, player ->
                player.copy(number = index + 1)
            }
    }

    @ColorRes
    private fun calculateColorForCurrentScore(finalScore: Int, currentScore: Int): Int {
        return when (100 * currentScore / finalScore) {
            in 0..25 -> R.color.uno_green
            in 26..50 -> R.color.uno_blue
            in 51..85 -> R.color.uno_yellow
            else -> R.color.uno_red
        }
    }

    @ColorRes
    private fun calculateColorForRoundScore(finalScore: Int, roundResult: RoundResult): Int {
        val p = 100 * roundResult.result / finalScore
        if (roundResult.round == START_SCORE_ROUND) return R.color.purple_200
        return when (p) {
            in 0..10 -> R.color.uno_green
            in 11..20 -> R.color.uno_blue
            in 21..35 -> R.color.uno_yellow
            else -> R.color.uno_red
        }
    }
}