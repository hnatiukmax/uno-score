package dev.hnatiuk.uno_score.presentation.pages.host

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.uno_score.presentation.pages.start.StartFragment
import javax.inject.Inject

sealed class HostEvent : Event

@HiltViewModel
class HostViewModel @Inject constructor(
    private val applicationRouter: ApplicationRouter
) : BaseViewModel<HostEvent>() {

    fun launch() {
        applicationRouter.newRootScreen(StartFragment.screen())
    }
}