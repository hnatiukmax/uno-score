package dev.hnatiuk.uno_score.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import dev.hnatiuk.core.presentation.delegates.TextViewGoneIfEmptyDelegate
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.ViewTitledValueBinding

class TitledValueView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = R.style.Widget_TitledValueContainer
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding = ViewTitledValueBinding.inflate(LayoutInflater.from(context), this)

    var title by TextViewGoneIfEmptyDelegate(binding.title)
    var value by TextViewGoneIfEmptyDelegate(binding.value)

    init {
        orientation = HORIZONTAL
        findAttributes(attrs, defStyleAttr)
    }

    private fun findAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(attrs, R.styleable.TitledValueView, defStyleAttr) {
            title = getString(R.styleable.TitledValueView_tv_title)
            value = getString(R.styleable.TitledValueView_tv_value)
            setValueTextColor(getColor(R.styleable.TitledValueView_tv_valueColor, R.color.black))
        }
    }

    private fun setValueTextColor(@ColorRes colorRes: Int) {
        binding.value.setTextColor(colorRes)
    }
}