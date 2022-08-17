package dev.hnatiuk.uno_score.presentation.recyclerview.items

import androidx.annotation.ColorRes
import dev.hnatiuk.uno_score.domain.entity.Score

sealed class FinalScoreSuggestionItem {

    data class ScoreItem(
        val score: Score,
        @ColorRes val backgroundColorRes: Int
    ) : FinalScoreSuggestionItem()

    object CustomItem : FinalScoreSuggestionItem()
}