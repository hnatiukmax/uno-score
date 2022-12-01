package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import android.view.View
import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.uno_score.databinding.ItemPlayerBinding
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem

fun playerAdapterDelegate(
    onPlayerClick: (Player, View) -> Unit,
) = diffAdapterDelegateLayoutContainer<PlayerItem.Item, PlayerItem, ItemPlayerBinding>(
    same = { oldItem: PlayerItem.Item, newItem: PlayerItem.Item -> oldItem.data.id == newItem.data.id },
    contentEquals = { oldItem: PlayerItem.Item, newItem: PlayerItem.Item ->
        oldItem.number == newItem.number &&
                oldItem.data.name == newItem.data.name &&
                oldItem.data.loseCount == newItem.data.loseCount
    },
    viewBinding = { inflater, root -> ItemPlayerBinding.inflate(inflater, root, false) }
) {
    with(binding) {
        root.setOnClickListener {
            onPlayerClick.invoke(item.data, it)
        }
    }

    bind {
        with(binding) {
            number.text = "${item.number}"
            name.text = item.data.name
            loseCount.text = "${item.data.loseCount}"
        }
    }
}