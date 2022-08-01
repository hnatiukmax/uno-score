package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.uno_score.databinding.ItemFinalScoreBinding
import dev.hnatiuk.uno_score.domain.entity.Score

fun finalScoreAdapterDelegate(
    onScoreClick: (Score) -> Unit
) = diffAdapterDelegateLayoutContainer<Score, Any, ItemFinalScoreBinding>(
    viewBinding = { inflater, root -> ItemFinalScoreBinding.inflate(inflater, root, false) }
) {
    itemView.setOnClickListener {
        onScoreClick(item)
    }

    bind {
        with(binding) {
            score.text = item.value.toString()
        }
    }
}