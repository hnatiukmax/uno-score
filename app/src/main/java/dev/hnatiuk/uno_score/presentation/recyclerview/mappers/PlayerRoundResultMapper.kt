package dev.hnatiuk.uno_score.presentation.recyclerview.mappers;

import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerRoundResultItem

object PlayerRoundResultMapper {

    fun mapToItems(list: List<Player>): List<PlayerRoundResultItem> {
        return list.mapIndexed { index, player ->
            PlayerRoundResultItem.SetResult(
                id = player.id,
                number = index + 1,
                name = player.name
            )
        }
    }
}