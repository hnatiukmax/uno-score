package dev.hnatiuk.core.presentation.utils

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu

class SimplePopupMenu(
    context: Context,
    view: View,
    @MenuRes menuRes: Int,
    onItemClick: (MenuItem) -> Unit
) : PopupMenu(context, view) {

    init {
        inflate(menuRes)
        setOnMenuItemClickListener {
            onItemClick.invoke(it)
            true
        }
    }

    companion object {

        fun View.showPopupMenu(
            @MenuRes menuRes: Int,
            onItemClick: (MenuItem) -> Unit
        ) = SimplePopupMenu(context, this, menuRes, onItemClick).also {
            it.show()
        }
    }
}