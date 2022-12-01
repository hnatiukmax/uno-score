package dev.hnatiuk.uno_score.presentation.pages.score

import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.binding.bindVisibility
import dev.hnatiuk.core.presentation.extensions.addDivider
import dev.hnatiuk.core.presentation.extensions.makeScrollable
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.utils.SimplePopupMenu.Companion.showPopupMenu
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.FragmentScoreBinding
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialog.Companion.setFragmentSingleResultListener
import dev.hnatiuk.uno_score.presentation.pages.score.ScoreViewModel.Companion.ADD_NEW_PLAYER_REQUEST_KEY
import dev.hnatiuk.uno_score.presentation.pages.score.ScoreViewModel.Companion.NEW_FINAL_SCORE_REQUEST_KEY
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.gamePlayerAdapterDelegate
import dev.hnatiuk.uno_score.presentation.recyclerview.items.GamePlayerItem

@AndroidEntryPoint
class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel, ScoreEvent>() {

    override val inflate: Inflate<FragmentScoreBinding> = FragmentScoreBinding::inflate

    override val viewModel by viewModels<ScoreViewModel>()

    private val playersAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            gamePlayerAdapterDelegate(::showPlayerActions)
        )
    }

    override fun FragmentScoreBinding.initUI() {
        initPlayers()
        setupFragmentResultListeners()
        setupClickListeners()
        loser.makeScrollable(true)
    }

    override fun ScoreViewModel.observeViewModel() {
        binding.playersEmpty.bindVisibility(viewLifecycleOwner, isPlayersEmptyVisible)
        finalScore.observe(viewLifecycleOwner) { binding.finalScore.value = it.toString() }
        playersAdapter.bind(viewLifecycleOwner, playerItems)
        round.observe(viewLifecycleOwner) {
            binding.calculate.text = getString(R.string.score_calculate_for_round, it.toString())
            binding.round.value = it.toString()
        }
        losers.observe(viewLifecycleOwner) { losers ->
            binding.activeGameGroup.isVisible = losers.isEmpty()
            binding.endedGameGroup.isVisible = losers.isNotEmpty()
            if (losers.isNotEmpty()) {
                binding.loser.text = losers.joinToString(", ") { it.player.name }
                binding.loserScore.text = losers.first().score.toString()
            }
        }
    }

    private fun initPlayers() = with(binding) {
        players.adapter = playersAdapter
        players.addDivider(R.dimen.margin_divider_player_item, RecyclerView.VERTICAL)
    }

    private fun setupClickListeners() = with(binding) {
        finalScore.setOnClickListener { viewModel.onChangeFinalScoreClick() }
        addPlayer.setOnClickListener { viewModel.onAddPlayerClick() }
        calculate.setOnClickListener { viewModel.onCalculateClick() }
        reset.setOnClickListener { viewModel.onResetClick() }
        finish.setOnClickListener { viewModel.onFinishClick() }
        settings.setOnClickListener { showSettingsMenu(it) }
        playersList.setOnClickListener { viewModel.onPlayerListClick() }
    }

    private fun setupFragmentResultListeners() {
        setFragmentSingleResultListener(ADD_NEW_PLAYER_REQUEST_KEY, viewModel::addPlayer)
        setFragmentSingleResultListener(NEW_FINAL_SCORE_REQUEST_KEY) {
            viewModel.onFinalScoreSelected(
                it.toIntOrNull() ?: return@setFragmentSingleResultListener
            )
        }
    }

    private fun showSettingsMenu(view: View) {
        view.showPopupMenu(R.menu.menu_settings, viewModel::handleSettingsMenu)
    }

    private fun showPlayerActions(view: View, item: GamePlayerItem.Player) {
        view.showPopupMenu(R.menu.menu_item_player_actions) {
            when (it.itemId) {
                R.id.delete -> viewModel.onPlayerDelete(item)
            }
        }
    }

    companion object {

        fun screen(score: Int) = FragmentScreen {
            ScoreFragment().apply {
                arguments = bundleOf(ScoreViewModel.SCORE_ARG to score)
            }
        }
    }
}