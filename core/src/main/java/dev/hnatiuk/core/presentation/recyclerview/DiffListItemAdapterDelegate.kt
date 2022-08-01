package dev.hnatiuk.core.presentation.recyclerview

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

data class DiffListItemAdapterDelegate<I>(
	val diffItemCallback: DiffUtilCallbackDelegate<I>,
	val delegate: AdapterDelegate<List<I>>
)
