package dev.hnatiuk.uno_score.presentation.pages.players

import android.text.InputType
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.clear
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.core.presentation.utils.asStringRes
import dev.hnatiuk.uno_score.R.id
import dev.hnatiuk.uno_score.R.string
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.repository.PlayerRepository
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialog
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialogArgs
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PlayersEvent : Event

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val applicationRouter: ApplicationRouter,
    private val playerRepository: PlayerRepository
) : BaseViewModel<PlayersEvent>() {

    val players = MutableLiveData<List<PlayerItem>>()
    val isEmptyState = players.map { it.isEmpty() }

    val newPlayerName = MutableLiveData<String>()

    override fun onViewLoaded() {
        subscribeOnPlayers()
    }

    fun handlePlayerMenu(item: MenuItem, player: Player) {
        when (item.itemId) {
            id.delete -> deletePlayer(player)
            id.setLoseCount -> applicationRouter.showDialog(
                InputDialog.screen(
                    InputDialogArgs(
                        requestKey = LOSE_COUNT_REQUEST_KEY,
                        title = string.set_lose_count_title.asStringRes,
                        inputHint = string.set_lose_count_input_hint.asStringRes,
                        positiveButton = string.set_lose_count_apply_button_text.asStringRes,
                        inputType = InputType.TYPE_CLASS_NUMBER,
                        payload = player
                    )
                )
            )
        }
    }

    fun onAddPlayerClick() {
        val name = newPlayerName.value ?: return
        viewModelScope.launch {
            playerRepository.addPlayer(name)
            newPlayerName.clear()
        }
    }

    fun onSetLoseCount(loseCountValue: String, player: Player?) {
        val loseCount = loseCountValue.toIntOrNull()
        if (loseCount != null && player != null) {
            viewModelScope.launch {
                playerRepository.setPlayerLoseCount(player.id, loseCount)
            }
        }
    }

    private fun deletePlayer(player: Player) {
        viewModelScope.launch {
            playerRepository.removePlayer(player.id)
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

    private fun mapToItems(players: List<Player>): List<PlayerItem> {
        return players.mapIndexed { index, player ->
            PlayerItem.Item(
                number = index.plus(1),
                data = player
            )
        }
    }

    companion object {

        const val LOSE_COUNT_REQUEST_KEY = "LOSE_COUNT_REQUEST_KEY"
    }
}