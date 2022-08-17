package dev.hnatiuk.uno_score.presentation.pages.roundresult

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerRoundResultItem
import dev.hnatiuk.uno_score.presentation.recyclerview.mappers.PlayerRoundResultMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoundResultEvent : Event {

    object OnClose : RoundResultEvent()
}

@HiltViewModel
class RoundResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository
) : BaseViewModel<RoundResultEvent>() {

    private val gameId = savedStateHandle.get<Int>(GAME_ID_ARG)
        ?: throw IllegalArgumentException("No gameId arg")

    private val roundResults = mutableMapOf<Int, Int>()
    private val _game = MutableLiveData<UnoGame>()

    val round: LiveData<Int> = _game.map { it.round }
    val playerItems: LiveData<List<PlayerRoundResultItem>> = _game.map {
        PlayerRoundResultMapper.mapToItems(it.players)
    }

    override fun onViewLoaded() {
        viewModelScope.launch {
            gameRepository.getGameFlow(gameId)
                .distinctUntilChanged()
                .collectLatest {
                    _game.value = it
                }
        }
    }

    fun onApplyClick() {
        if (playerItems.value?.size != roundResults.size) {
            postMessage(R.string.round_result_all_players_warning.asResourceMessage)
        } else {
            viewModelScope.launch {
                gameRepository.setRoundResult(gameId, roundResults)
                processEvent(RoundResultEvent.OnClose)
            }
        }
    }

    fun onPlayerScoreChanged(item: PlayerRoundResultItem.SetResult, score: String) {
        val intScore = score.toIntOrNull() ?: return
        roundResults[item.id] = intScore
    }

    companion object {

        const val GAME_ID_ARG = "GAME_ID_ARG"
    }
}
