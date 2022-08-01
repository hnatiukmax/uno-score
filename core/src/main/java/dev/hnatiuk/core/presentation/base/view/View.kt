package dev.hnatiuk.core.presentation.base.view

import androidx.viewbinding.ViewBinding
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event

interface View<VB : ViewBinding, VM : BaseViewModel<E>, E : Event> {

    fun VB.initUI()

    fun VB.bind()

    fun VM.observeViewModel()

    fun handleEvent(event: Event)
}