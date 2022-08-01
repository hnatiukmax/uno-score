package dev.hnatiuk.core.presentation.recyclerview.decorator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class DividerDecorator(
    context: Context,
    orientation: Int = VERTICAL,
    var paddingLeft: Int = 0,
    var paddingRight: Int = 0,
    var isAll: Boolean = false
) : DividerItemDecoration(context, orientation) {

    var divider: Drawable? = null
        set(value) {
            field = value
            divider?.let { setDrawable(it) }
        }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerPadding = Rect()
        divider?.getPadding(dividerPadding)
        val dividerLeft = parent.paddingLeft + paddingLeft + dividerPadding.left
        val dividerRight = parent.width - parent.paddingRight - paddingRight - dividerPadding.right
        val childCount = if (isAll) parent.childCount else parent.childCount - 1

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val intrinsicHeight = divider?.intrinsicHeight ?: 0
            val dividerBottom = dividerTop + intrinsicHeight
            divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider?.draw(canvas)
        }
    }
}

class MarginItemDecoration(
    private val spaceSize: Int,
    @RecyclerView.Orientation private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
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