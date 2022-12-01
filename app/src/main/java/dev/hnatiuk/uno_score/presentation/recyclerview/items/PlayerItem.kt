package dev.hnatiuk.uno_score.presentation.recyclerview.items

import dev.hnatiuk.uno_score.domain.entity.Player

sealed class PlayerItem {

    data class Item(
        val number: Int,
        val data: Player
    ) : PlayerItem()
}