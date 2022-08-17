package dev.hnatiuk.uno_score.domain.entity

data class Player(
    val id: Int,
    val name: String,
    val roundResults: List<RoundResult>
) {

    val score get() = roundResults.sumOf { it.result }
}