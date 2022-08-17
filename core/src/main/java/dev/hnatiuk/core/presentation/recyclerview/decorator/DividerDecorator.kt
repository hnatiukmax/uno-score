package dev.hnatiuk.core.presentation.recyclerview.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceSize: Int,
    @RecyclerView.Orientation private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (orientation == RecyclerView.VERTICAL) {
                top = spaceSize
                bottom = spaceSize
            } else {
                left = spaceSize
                right = spaceSize
            }
        }
    }
}

class GridItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = spaceSize
            bottom = spaceSize
            left = spaceSize
            right = spaceSize
        }
    }
}