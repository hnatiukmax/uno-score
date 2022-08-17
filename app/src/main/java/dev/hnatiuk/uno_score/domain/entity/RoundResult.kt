package dev.hnatiuk.uno_score.domain.entity

data class RoundResult(
    val round: Int,
    val result: Int
) {

    companion object {

        const val START_SCORE_ROUND = -1
    }
}