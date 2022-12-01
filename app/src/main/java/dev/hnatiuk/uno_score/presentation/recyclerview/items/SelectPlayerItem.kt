package dev.hnatiuk.uno_score.presentation.recyclerview.items;

import dev.hnatiuk.uno_score.domain.entity.Player

sealed class SelectPlayerItem {

    data class Select(
        val selected: Boolean,
        val number: Int,
        val data: Player
    ) : SelectPlayerItem()
}