package dev.hnatiuk.uno_score.presentation.recyclerview.adapter

import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import dev.hnatiuk.core.presentation.extensions.doOnEditorAction
import dev.hnatiuk.core.presentation.extensions.makeScrollable
import dev.hnatiuk.core.presentation.recyclerview.diffAdapterDelegateLayoutContainer
import dev.hnatiuk.core.presentation.utils.showKeyboardWithDelay
import dev.hnatiuk.uno_score.databinding.ItemPlayerRoundResultBinding
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerRoundResultItem

fun playerRoundResultAdapterDelegate(
    onScoreChanged: (PlayerRoundResultItem.SetResult, String) -> Unit,
    onDoneClick: () -> Unit
) = diffAdapterDelegateLayoutContainer<PlayerRoundResultItem.SetResult, PlayerRoundResultItem, ItemPlayerRoundResultBinding>(
    viewBinding = { inflater, root ->
        ItemPlayerRoundResultBinding.inflate(
            inflater,
            root,
            false
        )
    }
) {
    with(binding) {
        name.makeScrollable(true)
        score.doOnEditorAction(EditorInfo.IME_ACTION_DONE, onDoneClick)
        score.doOnTextChanged { text, _, _, _ ->
            onScoreChanged.invoke(item, text.toString())
        }
    }

    bind {
        with(binding) {
            number.text = "${item.id}"
            name.text = item.name
            if (adapterPosition == 0) context.showKeyboardWithDelay(score)
        }
    }
}