package dev.hnatiuk.uno_score.presentation.pages.score

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.getOrException
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.domain.entity.GameParticipant
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import dev.hnatiuk.uno_score.presentation.GameInteractor
import dev.hnatiuk.uno_score.presentation.navigation.showAddNewPlayerInputDialog
import dev.hnatiuk.uno_score.presentation.navigation.showSetFinalScoreInputDialog
import dev.hnatiuk.uno_score.presentation.pages.players.PlayersFragment
import dev.hnatiuk.uno_score.presentation.pages.roundresult.RoundResultDialog
import dev.hnatiuk.uno_score.presentation.pages.selectplayers.SelectPlayersDialog
import dev.hnatiuk.uno_score.presentation.recyclerview.items.GamePlayerItem
import dev.hnatiuk.uno_score.presentation.recyclerview.mappers.PlayerItemMapper.mapPlayersToPlayerItems
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ScoreEvent : Event

@HiltViewModel
class ScoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val applicationRouter: ApplicationRouter,
    private val gameInteractor: GameInteractor
) : BaseViewModel<ScoreEvent>() {

    private val scoreArg = savedStateHandle.getOrException<Int>(SCORE_ARG)

    private var currentGameFlowJob: Job? = null
    private val game get() = _game.getOrException()

    private val _game = MutableLiveData<UnoGame>()

    val playerItems = _game.map { mapPlayersToPlayerItems(it.finalScore, it.players) }
    val isPlayersEmptyVisible = _game.map { it.players.isEmpty() }
    val finalScore = _game.map { it.finalScore }
    val round = _game.map { it.round }
    val losers = _game.map { findLosers(it.finalScore, it.players) }

    override fun onViewLoaded() {
        viewModelScope.launch {
            gameInteractor.startGame(scoreArg)
            subscribeOnGame()
        }
    }

    fun onPlayerDelete(item: GamePlayerItem.Player) {
        viewModelScope.launch {
            gameInteractor.removePlayers(item.id)
        }
    }

    fun onFinalScoreSelected(score: Int) {
        viewModelScope.launch {
            gameInteractor.setFinalScore(score)
        }
    }

    fun addPlayer(playerName: String) {
        viewModelScope.launch {
            gameInteractor.addNewPlayer(playerName)
        }
    }

    fun onCalculateClick() {
        if (playerItems.value.isNullOrEmpty()) {
            postMessage(R.string.score_players_empty_warning.asResourceMessage)
        } else {
            applicationRouter.showDialog(RoundResultDialog.screen(game.id))
        }
    }

    fun handleSettingsMenu(item: MenuItem) {
        when (item.itemId) {
            R.id.reset -> onResetClick()
            R.id.clear -> onClearClick()
            R.id.players -> applicationRouter.navigateTo(PlayersFragment.screen())
        }
    }

    fun onResetClick() {
        viewModelScope.launch {
            gameInteractor.resetGame()
            subscribeOnGame()
        }
    }

    fun onClearClick() {

    }

    /* Navigation */

    fun onChangeFinalScoreClick() {
        applicationRouter.showSetFinalScoreInputDialog(NEW_FINAL_SCORE_REQUEST_KEY, game.finalScore)
    }

    fun onAddPlayerClick() {
        applicationRouter.showAddNewPlayerInputDialog(ADD_NEW_PLAYER_REQUEST_KEY)
    }

    fun onPlayerListClick() {
        applicationRouter.showDialog(SelectPlayersDialog.screen(game.id))
    }

    fun onFinishClick() {
        applicationRouter.exit()
    }

    /* Navigation end */

    private fun subscribeOnGame() {
        currentGameFlowJob = viewModelScope.launch {
            gameInteractor.getGameFlow().collect(_game::setValue)
        }
    }

    private fun findLosers(finalScore: Int, items: List<GameParticipant>): List<GameParticipant> {
        return items.filter { player -> player.score >= finalScore }
    }

    companion object {

        const val SCORE_ARG = "SCORE_ARG"

        const val ADD_NEW_PLAYER_REQUEST_KEY = "ADD_NEW_PLAYER_REQUEST_KEY"
        const val NEW_FINAL_SCORE_REQUEST_KEY = "NEW_FINAL_SCORE_REQUEST_KEY"
    }
}