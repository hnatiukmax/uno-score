package dev.hnatiuk.uno_score.presentation.recyclerview.items

import androidx.annotation.ColorRes

sealed class PlayerItem {

    data class Player(
        val id: Int,
        val number: Int,
        val name: String,
        val roundHistory: List<RoundResult>,
        val score: Int,
        @ColorRes val scoreBackgroundColor: Int
    ) : PlayerItem() {

        data class RoundResult(
            val round: Int,
            val score: Int,
            @ColorRes val color: Int
        )
    }
}