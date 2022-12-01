package dev.hnatiuk.uno_score.presentation.recyclerview.mappers

import androidx.annotation.ColorRes
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.domain.entity.GameParticipant
import dev.hnatiuk.uno_score.domain.entity.RoundResult
import dev.hnatiuk.uno_score.domain.entity.RoundResult.Companion.START_SCORE_ROUND
import dev.hnatiuk.uno_score.presentation.recyclerview.items.GamePlayerItem

object PlayerItemMapper {

    fun mapPlayersToPlayerItems(finalScore: Int, list: List<GameParticipant>): List<GamePlayerItem> {
        return list.mapIndexed { participantIndex, participant ->
            GamePlayerItem.Player(
                id = participant.player.id,
                number = participantIndex + 1,
                name = participant.player.name,
                roundHistory = participant.roundResults.map { result ->
                    GamePlayerItem.Player.RoundResult(
                        round = result.round,
                        score = result.result,
                        color = calculateColorForRoundScore(finalScore, result)
                    )
                },
                score = participant.score,
                scoreBackgroundColor = calculateColorForCurrentScore(finalScore, participant.score)
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