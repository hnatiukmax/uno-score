package dev.hnatiuk.uno_score.presentation.pages.score

import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.binding.bindVisibility
import dev.hnatiuk.core.presentation.extensions.*
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.utils.hideKeyboard
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.FragmentScoreBinding
import dev.hnatiuk.uno_score.presentation.pages.editscore.EditFinalScoreDialog
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.playerAdapterDelegate
import dev.hnatiuk.uno_score.presentation.recyclerview.items.PlayerItem

@AndroidEntryPoint
class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel, ScoreEvent>() {

    override val inflate: Inflate<FragmentScoreBinding> = FragmentScoreBinding::inflate

    override val viewModel by viewModels<ScoreViewModel>()

    private val playersAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            playerAdapterDelegate(::showPlayerActions)
        )
    }

    override fun FragmentScoreBinding.initUI() {
        initPlayers()
        setupFragmentResultListeners()
        setupClickListeners()
        loser.makeScrollable(true)
    }

    override fun ScoreViewModel.observeViewModel() {
        binding.newPlayerName.bind(viewLifecycleOwner, newPlayerName, viewModel::onNewPlayerNameChanged)
        binding.newPlayerScore.bind(viewLifecycleOwner, newPlayerScore)
        binding.playersEmpty.bindVisibility(viewLifecycleOwner, isPlayersEmptyVisible)
        binding.score.bind(viewLifecycleOwner, finalScore.map { it.toString() })
        playersAdapter.bind(viewLifecycleOwner, playerItems)
        round.observe(viewLifecycleOwner) {
            binding.calculate.text = getString(R.string.score_calculate_for_round, it.toString())
            binding.round.text = it.toString()
        }
        losers.observe(viewLifecycleOwner) { losers ->
            binding.activeGameGroup.isVisible = losers.isEmpty()
            binding.endedGameGroup.isVisible = losers.isNotEmpty()
            if (losers.isNotEmpty()) {
                binding.loser.text = losers.joinToString(", ") { it.name }
                binding.loserScore.text = losers.first().score.toString()
            }
        }
    }

    private fun initPlayers() = with(binding) {
        players.adapter = playersAdapter
        players.addDivider(R.dimen.margin_divider_player_item, RecyclerView.VERTICAL)
    }

    private fun setupClickListeners() = with(binding) {
        finalScoreContainer.setOnClickListener { viewModel.onChangeFinalScoreClick() }
        listOf(newPlayerName, newPlayerScore).forEach {
            it.doOnEditorAction(EditorInfo.IME_ACTION_DONE) {
                requireContext().hideKeyboard(it)
                viewModel.onAddPlayerClick()
            }
        }
        addPlayer.setOnClickListener {
            viewModel.onAddPlayerClick()
            newPlayerName.requestFocus()
        }
        calculate.setOnClickListener { viewModel.onCalculateClick() }
        reset.setOnClickListener { viewModel.onResetClick() }
        finish.setOnClickListener { viewModel.onFinishClick() }
    }

    private fun setupFragmentResultListeners() {
        setFragmentResultListener(EditFinalScoreDialog.NEW_SCORE_REQUEST_KEY) { _, bundle ->
            val newScore = bundle.getInt(EditFinalScoreDialog.NEW_SCORE_ARG)
            viewModel.onFinalScoreSelected(newScore)
        }
    }

    private fun showPlayerActions(view: View, item: PlayerItem.Player) {
        PopupMenu(requireContext(), view).apply {
            inflate(R.menu.menu_item_player_actions)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        viewModel.onPlayerDelete(item)
                        true
                    }
                    else -> false
                }
            }
        }.also {
            it.show()
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