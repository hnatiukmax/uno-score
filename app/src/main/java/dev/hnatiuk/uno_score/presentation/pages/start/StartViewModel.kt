package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import javax.inject.Inject

sealed class StartEvent : Event

@HiltViewModel
class StartViewModel @Inject constructor(
    private val applicationRouter: ApplicationRouter
) : BaseViewModel<StartEvent>() {

    val finalScoreData = MutableLiveData<String>()

    fun onFinalScoreChanged(value: String) {

    }

    fun onStartClick() {

    }
}