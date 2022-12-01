package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import android.content.res.ColorStateList
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dev.hnatiuk.core.presentation.extensions.addDividerDp
import dev.hnatiuk.core.presentation.extensions.dpToPx
import dev.hnatiuk.core.presentation.extensions.makeScrollable
import dev.hnatiuk.core.presentation.extensions.withAlpha
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.uno_score.databinding.ItemGamePlayerBinding
import dev.hnatiuk.uno_score.databinding.ItemRoundHistoryBinding
import dev.hnatiuk.uno_score.domain.entity.RoundResult.Companion.START_SCORE_ROUND
import dev.hnatiuk.uno_score.presentation.recyclerview.items.GamePlayerItem
import dev.hnatiuk.uno_score.presentation.recyclerview.items.GamePlayerItem.Player
import dev.hnatiuk.uno_score.presentation.recyclerview.items.GamePlayerItem.Player.RoundResult

fun gamePlayerAdapterDelegate(
    onItemClick: (View, Player) -> Unit
) = diffAdapterDelegateLayoutContainer<Player, GamePlayerItem, ItemGamePlayerBinding>(
    viewBinding = { inflater, root -> ItemGamePlayerBinding.inflate(inflater, root, false) },
    on = { item: Any, _: List<Any>, _: Int -> item is Player },
    same = { oldItem: Player, newItem: Player -> oldItem.id == newItem.id },
    contentEquals = { oldItem: Player, newItem: Player ->
        oldItem.number == newItem.number &&
                oldItem.name == newItem.name &&
                oldItem.score == newItem.score &&
                oldItem.roundHistory == newItem.roundHistory
    }
) {
    val historyAdapter = AsyncListDiffDelegationAdapter(playerRoundHistoryItemAdapterDelegate())
    with(binding) {
        root.setOnClickListener { onItemClick.invoke(itemView, item) }
        roundHistory.adapter = historyAdapter
        roundHistory.addDividerDp(context.dpToPx(2), RecyclerView.HORIZONTAL)
        name.makeScrollable(true)
    }

    bind {
        with(binding) {
            number.text = item.number.toString()
            name.text = item.name
            score.text = item.score.toString()
            score.backgroundTintList = ColorStateList.valueOf(
                getColor(item.scoreBackgroundColor).withAlpha(0.7f)
            )
            historyAdapter.items = item.roundHistory
        }
    }
}

fun playerRoundHistoryItemAdapterDelegate() =
    diffAdapterDelegateLayoutContainer<RoundResult, Any, ItemRoundHistoryBinding>(
        viewBinding = { inflater, root -> ItemRoundHistoryBinding.inflate(inflater, root, false) },
        on = { item: Any, _: List<Any>, _: Int -> item is RoundResult },
        same = { oldItem: RoundResult, newItem: RoundResult -> oldItem.round == newItem.round },
        contentEquals = { oldItem: RoundResult, newItem: RoundResult ->
            oldItem.score == newItem.score && oldItem.color == newItem.color
        }
    ) {
        bind {
            with(binding) {
                score.text = item.score.toString()
                score.backgroundTintList = ColorStateList.valueOf(
                    getColor(item.color).withAlpha(0.2f)
                )
                startScoreDot.isVisible = item.round == START_SCORE_ROUND
            }
        }
    }
