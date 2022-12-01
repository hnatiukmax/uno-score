package dev.hnatiuk.uno_score.presentation.recyclerview.items

import androidx.annotation.ColorRes

sealed class GamePlayerItem {

    data class Player(
        val id: Int,
        val number: Int,
        val name: String,
        val roundHistory: List<RoundResult>,
        val score: Int,
        @ColorRes val scoreBackgroundColor: Int
    ) : GamePlayerItem() {

        data class RoundResult(
            val round: Int,
            val score: Int,
            @ColorRes val color: Int
        )
    }
}