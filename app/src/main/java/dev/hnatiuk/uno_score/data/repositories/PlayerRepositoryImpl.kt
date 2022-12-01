package dev.hnatiuk.uno_score.data.repositories

import dev.hnatiuk.uno_score.data.database.dao.PlayerDao
import dev.hnatiuk.uno_score.data.database.entity.PlayerEntity
import dev.hnatiuk.uno_score.data.mappers.PlayerEntityMapper.toPlayer
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao
) : PlayerRepository {

    override suspend fun addPlayer(name: String) {
        playerDao.insertPlayer(
            PlayerEntity(
                name = name,
                loseCount = 0
            )
        )
    }

    override suspend fun removePlayer(id: Int) {
        playerDao.removePlayer(id)
    }

    override suspend fun setPlayerLoseCount(playerId: Int, loseCount: Int) {
        playerDao.incrementPlayerLoseCount(playerId)
    }

    override fun getPlayers(): Flow<List<Player>> {
        return playerDao.getPlayers().map { players ->
            players.map { it.toPlayer() }
        }
    }

}