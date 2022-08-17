package dev.hnatiuk.uno_score.presentation.recyclerview.items;

sealed class PlayerRoundResultItem {

    data class SetResult(
        val id: Int,
        val number: Int,
        val name: String
    ) : PlayerRoundResultItem()
}