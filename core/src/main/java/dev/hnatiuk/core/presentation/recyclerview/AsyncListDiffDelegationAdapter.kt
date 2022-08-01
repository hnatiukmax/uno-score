package dev.hnatiuk.core.presentation.recyclerview

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class AsyncListDiffDelegationAdapter<T>(
    vararg delegate: DiffListItemAdapterDelegate<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onBindListener: ((Int) -> Unit)? = null

    private val diffCallbackDelegates = DiffUtilCallbackDelegatesManager<T>()
    private val delegatesManager: AdapterDelegatesManager<List<T>> = AdapterDelegatesManager()
    private val differ = AsyncListDiffer<T>(this, diffCallbackDelegates)

    var items: List<T>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    init {
        delegate.forEach { (diffItemCallback, adapterDelegate) ->
            delegatesManager.addDelegate(adapterDelegate)
            diffCallbackDelegates.delegates.add(diffItemCallback)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(differ.getCurrentList(), position, holder, null)
        onBindListener?.invoke(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<*>) {
        delegatesManager.onBindViewHolder(differ.getCurrentList(), position, holder, payloads)
        onBindListener?.invoke(position)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(differ.getCurrentList(), position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return delegatesManager.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun <I : T> subscribe(
        lifecycleOwner: LifecycleOwner,
        liveDate: LiveData<List<I>>
    ): Observer<List<I>> {
        val observer = Observer<List<I>> { items = it }
        liveDate.observe(lifecycleOwner, observer)
        return observer
    }
}