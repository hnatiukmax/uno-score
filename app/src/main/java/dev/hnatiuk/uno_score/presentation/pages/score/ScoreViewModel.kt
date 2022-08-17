package dev.hnatiuk.uno_score.presentation.pages.score

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.replace
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.domain.entity.UnoGame
import dev.hnatiuk.uno_score.domain.repository.GameRepository
import dev.hnatiuk.uno_score.presentation.pages.editscore.EditFinalScoreDialog
import dev.hnatiuk.uno_score.presentation.pages.roundresult.RoundResultDialog
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem
import dev.hnatiuk.uno_score.presentation.recyclerview.mappers.PlayerItemMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ScoreEvent : Event

@HiltViewModel
class ScoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val applicationRouter: ApplicationRouter,
    private val gameRepository: GameRepository
) : BaseViewModel<ScoreEvent>() {

    private val scoreArg = savedStateHandle.get<Int>(SCORE_ARG)
        ?: throw IllegalArgumentException("No score argument")

    private var currentGameFlowJob: Job? = null
    private val _game = MutableLiveData<UnoGame>()
    private val game get() = _game.value ?: throw IllegalArgumentException("No created game")

    val playerItems: LiveData<List<PlayerItem>> =
        _game.map { PlayerItemMapper.mapPlayersToPlayerItems(it.finalScore, it.players) }
    val isPlayersEmptyVisible = _game.map { it.players.isEmpty() }
    val finalScore: LiveData<Int> = _game.map { it.finalScore }
    val round: LiveData<Int> = _game.map { it.round }
    val losers: LiveData<List<Player>> = _game.map { findLosers(it.finalScore, it.players) }

    val newPlayerName = MutableLiveData(NEW_PLAYER_NAME_DEFAULT)
    val newPlayerScore = MutableLiveData(NEW_PLAYER_SCORE_DEFAULT)

    override fun onViewLoaded() {
        startGame()
    }

    fun onPlayerDelete(item: PlayerItem.Player) {
        viewModelScope.launch {
            gameRepository.removePlayer(game.id, item.id)
        }
    }

    fun onNewPlayerNameChanged(value: String) {
        newPlayerName.value = value
        newPlayerScore.replace { score ->
            when {
                value.isNotBlank() && score.isNullOrBlank() -> NEW_PLAYER_SCORE_ZERO
                value.isBlank() && score.isNullOrBlank()
                    .not() && score == NEW_PLAYER_SCORE_ZERO -> NEW_PLAYER_SCORE_DEFAULT
                else -> score
            }
        }
    }

    fun onChangeFinalScoreClick() {
        val score = _game.value?.finalScore ?: return
        applicationRouter.showDialog(EditFinalScoreDialog.screen(isEditMode = true, game.id, score))
    }

    fun onFinalScoreSelected(score: Int) {
        viewModelScope.launch {
            gameRepository.setFinalScore(game.id, score)
        }
    }

    fun onAddPlayerClick() {
        val score = newPlayerScore.replace { NEW_PLAYER_SCORE_DEFAULT }?.toIntOrNull() ?: return
        val name =
            newPlayerName.replace { NEW_PLAYER_NAME_DEFAULT }?.takeUnless { it.isBlank() } ?: return

        viewModelScope.launch {
            gameRepository.addPlayer(game.id, name, score)
        }
    }

    fun onCalculateClick() {
        if (playerItems.value.isNullOrEmpty()) {
            postMessage(R.string.score_players_empty_warning.asResourceMessage)
        } else {
            applicationRouter.showDialog(RoundResultDialog.screen(game.id))
        }
    }

    fun onResetClick() {
        val currentPlayers = game.players.map { it.name }
        currentGameFlowJob?.cancel()
        startGame(currentPlayers)
    }

    fun onFinishClick() {
        applicationRouter.exit()
    }

    private fun startGame(startPlayers: List<String> = emptyList()) {
        currentGameFlowJob = viewModelScope.launch {
            val id = gameRepository.createGame(scoreArg, startPlayers)
            gameRepository.getGameFlow(id)
                .distinctUntilChanged()
                .collect { result ->
                    _game.value = result
                }
        }
    }

    private fun findLosers(finalScore: Int, items: List<Player>): List<Player> {
        return items.filter { player -> player.score >= finalScore }
    }

    companion object {

        const val SCORE_ARG = "SCORE_ARG"

        private const val NEW_PLAYER_SCORE_ZERO = "0"
        private const val NEW_PLAYER_NAME_DEFAULT = ""
        private const val NEW_PLAYER_SCORE_DEFAULT = ""
    }
}