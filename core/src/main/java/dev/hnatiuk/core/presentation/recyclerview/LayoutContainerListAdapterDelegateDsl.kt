package dev.hnatiuk.core.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateLayoutContainerViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

//inline fun <reified I : T, T : Any> diffAdapterDelegateLayoutContainer(
//    @LayoutRes layout: Int,
//    noinline on: (item: T, items: List<T>, position: Int) -> Boolean = { item, _, _ -> item is I },
//    noinline same: (oldItem: I, newItem: I) -> Boolean = { oldItem, newItem -> oldItem == newItem },
//    noinline contentEquals: (oldItem: I, newItem: I) -> Boolean = { oldItem, newItem -> oldItem.hashCode() == newItem.hashCode() },
//    noinline changePayload: (oldItem: I, newItem: I) -> Any? = { _, _ -> null },
//    noinline layoutInflater: (parent: ViewGroup, layoutRes: Int) -> View = { parent, layoutRes ->
//        LayoutInflater.from(parent.context).inflate(
//            layoutRes,
//            parent,
//            false
//        )
//    },
//    noinline block: AdapterDelegateLayoutContainerViewHolder<I>.() -> Unit
//) = DiffListItemAdapterDelegate<T>(
//    DslDiffUtilCallbackDelegate(on, same, contentEquals, changePayload),
//    adapterDelegateLayoutContainer(layout, on, layoutInflater, block)
//)

inline fun <reified I : T, T : Any, VB : ViewBinding> diffAdapterDelegateLayoutContainer(
    noinline on: (item: T, items: List<T>, position: Int) -> Boolean = { item, _, _ -> item is I },
    noinline same: (oldItem: I, newItem: I) -> Boolean = { oldItem, newItem -> oldItem == newItem },
    noinline contentEquals: (oldItem: I, newItem: I) -> Boolean = { oldItem, newItem -> oldItem.hashCode() == newItem.hashCode() },
    noinline changePayload: (oldItem: I, newItem: I) -> Any? = { _, _ -> null },
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline layoutInflater: (parent: ViewGroup) -> LayoutInflater = { parent -> LayoutInflater.from(parent.context) },
    noinline block: AdapterDelegateViewBindingViewHolder<I, VB>.() -> Unit
) = DiffListItemAdapterDelegate<T>(
    DslDiffUtilCallbackDelegate(on, same, contentEquals, changePayload),
    adapterDelegateViewBinding(viewBinding, on, layoutInflater, block)
)

@Suppress("UNCHECKED_CAST")
class DslDiffUtilCallbackDelegate<I : T, T : Any>(
    private val on: (item: T, items: List<T>, position: Int) -> Boolean,
    private val areSame: (oldItem: I, newItem: I) -> Boolean,
    private val areContentEquals: (oldItem: I, newItem: I) -> Boolean,
    private val changePayload: (oldItem: I, newItem: I) -> Any?
) : DiffUtilCallbackDelegate<T>() {

    override fun isForViewType(data: T): Boolean {
        return on(data, listOf(data), 0)
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areSame(oldItem as I, newItem as I)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentEquals(oldItem as I, newItem as I)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return changePayload(oldItem as I, newItem as I)
    }
}