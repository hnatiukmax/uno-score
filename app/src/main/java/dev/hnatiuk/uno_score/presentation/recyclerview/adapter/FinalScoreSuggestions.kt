package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import android.content.res.ColorStateList
import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.ItemFinalScoreSuggestionBinding
import dev.hnatiuk.uno_score.presentation.recyclerview.items.FinalScoreSuggestionItem

fun finalScoreSuggestionItemAdapterDelegate(
    onClick: (FinalScoreSuggestionItem.ScoreItem) -> Unit
) = diffAdapterDelegateLayoutContainer<FinalScoreSuggestionItem.ScoreItem, FinalScoreSuggestionItem, ItemFinalScoreSuggestionBinding>(
    viewBinding = { inflater, root ->
        ItemFinalScoreSuggestionBinding.inflate(inflater, root, false)
    }
) {
    itemView.setOnClickListener {
        onClick(item)
    }

    bind {
        with(binding) {
            score.text = item.score.value.toString()
            score.backgroundTintList = ColorStateList.valueOf(
                getColor(item.backgroundColorRes)
            )
        }
    }
}

fun customLabelScoreSuggestionAdapterDelegate(
    onClick: () -> Unit
) = diffAdapterDelegateLayoutContainer<FinalScoreSuggestionItem.CustomItem, FinalScoreSuggestionItem, ItemFinalScoreSuggestionBinding>(
    viewBinding = { inflater, root ->
        ItemFinalScoreSuggestionBinding.inflate(inflater, root, false)
    }
) {
    itemView.setOnClickListener {
        onClick()
    }

    bind {
        with(binding) {
            score.setText(R.string.start_custom)
            score.backgroundTintList = ColorStateList.valueOf(
                getColor(android.R.color.black)
            )
        }
    }
}