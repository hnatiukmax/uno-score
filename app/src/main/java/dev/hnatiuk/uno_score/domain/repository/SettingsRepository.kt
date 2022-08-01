package dev.hnatiuk.uno_score.domain.repository

import dev.hnatiuk.uno_score.domain.entity.Score

interface SettingsRepository {

    suspend fun getFinalScoreSuggestions(): List<Score>

    suspend fun getDefaultFinalScore(): Score
}