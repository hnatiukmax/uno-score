package dev.hnatiuk.uno_score.presentation.pages.selectplayers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import dev.hnatiuk.uno_score.domain.repository.PlayerRepository
import dev.hnatiuk.uno_score.presentation.pages.roundresult.RoundResultViewModel
import dev.hnatiuk.uno_score.presentation.recyclerview.items.SelectPlayerItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SelectPlayersEvent : Event {

    object OnClose : SelectPlayersEvent()
}

@HiltViewModel
class SelectPlayersViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: PlayerRepository,
    private val gameRepository: GameRepository
) : BaseViewModel<SelectPlayersEvent>() {

    private val gameId = savedStateHandle.get<Int>(RoundResultViewModel.GAME_ID_ARG)
        ?: throw IllegalArgumentException("No gameId arg")

    private val selectedPlayers = mutableSetOf<Player>()
    private val unselectedPlayers = mutableSetOf<Player>()

    val players = MutableLiveData<List<SelectPlayerItem>>()
    val isEmptyState = players.map { it.isEmpty() }

    override fun onViewLoaded() {
        subscribeOnPlayers()
    }

    fun onPlayerSelected(player: Player, selected: Boolean) {
        if (selected) {
            selectedPlayers.add(player)
            unselectedPlayers.remove(player)
        } else {
            selectedPlayers.remove(player)
            unselectedPlayers.add(player)
        }
    }

    fun onApplyClick() {
        viewModelScope.launch {
            gameRepository.addPlayers(gameId, *selectedPlayers.toTypedArray())
            gameRepository.removePlayers(gameId, *unselectedPlayers.map { it.id }.toIntArray())
            processEvent(SelectPlayersEvent.OnClose)
        }
    }

    private fun subscribeOnPlayers() {
        viewModelScope.launch {
            playerRepository.getPlayers()
                .distinctUntilChanged()
                .collect {
                    players.postValue(mapToItems(it))
                }
        }
    }

    private suspend fun mapToItems(players: List<Player>): List<SelectPlayerItem> {
        val gamePlayers = gameRepository.getGame(gameId)?.players
        return players.mapIndexed { index, player ->
            SelectPlayerItem.Select(
                number = index.plus(1),
                selected = gamePlayers?.any { it.player.id == player.id } ?: false,
                data = player
            )
        }
    }

    companion object {

        const val GAME_ID_ARG = "GAME_ID_ARG"
    }
}
