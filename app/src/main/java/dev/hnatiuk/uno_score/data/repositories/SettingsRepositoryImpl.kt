package dev.hnatiuk.uno_score.data.repositories

import dev.hnatiuk.uno_score.domain.entity.Score
import dev.hnatiuk.uno_score.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {

    private val finalScoreSuggestions = listOf(
        Score(100),
        Score(200),
        Score(300),
        Score(400),
        Score(500)
    )

    override suspend fun getFinalScoreSuggestions() = finalScoreSuggestions

    override suspend fun getDefaultFinalScore() = Score(200)
}