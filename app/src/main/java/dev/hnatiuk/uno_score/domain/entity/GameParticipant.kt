package dev.hnatiuk.uno_score.domain.entity

data class GameParticipant(
    val player: Player,
    val roundResults: List<RoundResult>
) {

    val score get() = roundResults.sumOf { it.result }
}