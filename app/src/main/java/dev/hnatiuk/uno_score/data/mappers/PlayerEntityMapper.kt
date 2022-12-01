package dev.hnatiuk.uno_score.data.mappers;

import dev.hnatiuk.uno_score.data.database.entity.PlayerEntity
import dev.hnatiuk.uno_score.domain.entity.Player

object PlayerEntityMapper {

    fun PlayerEntity.toPlayer() = Player(id, name, loseCount)
}