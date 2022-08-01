package dev.hnatiuk.uno_score.presentation.pages.editscore

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import javax.inject.Inject

sealed class EditScoreEvent : Event

@HiltViewModel
class EditScoreViewModel @Inject constructor() : BaseViewModel<EditScoreEvent>()