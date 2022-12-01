package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.uno_score.databinding.ItemSelectPlayerBinding
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.presentation.recyclerview.items.SelectPlayerItem

fun selectPlayerAdapterDelegate(
    onPlayerSelected: (Player, Boolean) -> Unit,
) = diffAdapterDelegateLayoutContainer<SelectPlayerItem.Select, SelectPlayerItem, ItemSelectPlayerBinding>(
    viewBinding = { inflater, root -> ItemSelectPlayerBinding.inflate(inflater, root, false) }
) {
    with(binding) {
        switchSelect.setOnCheckedChangeListener { _, isChecked ->
            onPlayerSelected.invoke(item.data, isChecked)
        }
    }

    bind {
        with(binding) {
            switchSelect.isChecked = item.selected
            number.text = "${item.number}"
            name.text = item.data.name
        }
    }
}