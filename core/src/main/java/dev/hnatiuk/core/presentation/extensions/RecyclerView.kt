package dev.hnatiuk.core.presentation.extensions

import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

import dev.hnatiuk.core.presentation.recyclerview.decorator.MarginItemDecoration

fun RecyclerView.addDividerDp(spaceSize: Int, @RecyclerView.Orientation orientation: Int) {
    addItemDecoration(MarginItemDecoration(spaceSize = spaceSize, orientation))
}

fun RecyclerView.addDivider(@DimenRes resId: Int, @RecyclerView.Orientation orientation: Int) {
    addItemDecoration(MarginItemDecoration(spaceSize = context.dimen(resId).toInt(), orientation))
}