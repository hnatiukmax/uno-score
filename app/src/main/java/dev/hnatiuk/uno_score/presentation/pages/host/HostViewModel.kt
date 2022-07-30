package dev.hnatiuk.uno_score.presentation.pages.host

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import javax.inject.Inject

sealed class HostEvent : Event

@HiltViewModel
class HostViewModel @Inject constructor() : BaseViewModel<HostEvent>()