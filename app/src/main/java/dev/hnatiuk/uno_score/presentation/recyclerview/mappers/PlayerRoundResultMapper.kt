package dev.hnatiuk.uno_score.presentation.recyclerview.mappers;

import dev.hnatiuk.uno_score.domain.entity.GameParticipant
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerRoundResultItem

object PlayerRoundResultMapper {

    fun mapToItems(list: List<GameParticipant>): List<PlayerRoundResultItem> {
        return list.mapIndexed { index, participant ->
            PlayerRoundResultItem.SetResult(
                id = participant.player.id,
                number = index + 1,
                name = participant.player.name
            )
        }
    }
}