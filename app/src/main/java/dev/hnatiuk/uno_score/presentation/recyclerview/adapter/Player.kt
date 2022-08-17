package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import dev.hnatiuk.core.presentation.extensions.*
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.recyclerview.decorator.MarginItemDecoration
import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.uno_score.databinding.ItemPlayerBinding
import dev.hnatiuk.uno_score.databinding.ItemRoundHistoryBinding
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem.Player
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem.Player.RoundResult

fun playerAdapterDelegate(
    onItemClick: (View, Player) -> Unit
) = diffAdapterDelegateLayoutContainer<Player, PlayerItem, ItemPlayerBinding>(
    viewBinding = { inflater, root -> ItemPlayerBinding.inflate(inflater, root, false) },
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
            }
        }
    }
