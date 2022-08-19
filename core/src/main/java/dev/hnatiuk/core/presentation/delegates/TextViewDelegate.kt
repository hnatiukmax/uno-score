package dev.hnatiuk.core.presentation.delegates

import android.widget.TextView
import androidx.core.view.isVisible
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TextViewGoneIfEmptyDelegate(private val view: TextView) :
    ReadWriteProperty<Any, CharSequence?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): CharSequence? {
        return view.text
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: CharSequence?) {
        view.isVisible = !value.isNullOrEmpty()
        view.text = value
    }
}