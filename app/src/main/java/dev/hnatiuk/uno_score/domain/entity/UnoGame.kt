package dev.hnatiuk.uno_score.domain.entity;

data class UnoGame(
    val id: Int,
    val finalScore: Int,
    val round: Int,
    val players: List<Player>
)