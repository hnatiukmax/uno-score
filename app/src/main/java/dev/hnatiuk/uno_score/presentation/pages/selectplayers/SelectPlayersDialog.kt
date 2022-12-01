package dev.hnatiuk.uno_score.presentation.pages.selectplayers

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseBottomDialog
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.binding.bindVisibility
import dev.hnatiuk.core.presentation.extensions.addDivider
import dev.hnatiuk.core.presentation.extensions.reverse
import dev.hnatiuk.core.presentation.navigation.DialogScreen
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.DialogSelectPlayersBinding
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.selectPlayerAdapterDelegate

@AndroidEntryPoint
class SelectPlayersDialog :
    BaseBottomDialog<DialogSelectPlayersBinding, SelectPlayersViewModel, SelectPlayersEvent>() {

    private val playersAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            selectPlayerAdapterDelegate(
                viewModel::onPlayerSelected
            )
        )
    }

    override val inflate: Inflate<DialogSelectPlayersBinding> = DialogSelectPlayersBinding::inflate

    override val viewModel by viewModels<SelectPlayersViewModel>()

    override fun DialogSelectPlayersBinding.initUI() {
        initPlayers()
        initListeners()
    }

    override fun SelectPlayersViewModel.observeViewModel() {
        binding.playersEmpty.bindVisibility(viewLifecycleOwner, isEmptyState)
        binding.players.bindVisibility(viewLifecycleOwner, isEmptyState.reverse())
        playersAdapter.bind(viewLifecycleOwner, players)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            SelectPlayersEvent.OnClose -> dismiss()
        }
    }

    private fun initPlayers() = with(binding) {
        players.adapter = playersAdapter
        players.addDivider(R.dimen.margin_divider_player_item, RecyclerView.VERTICAL)
    }

    private fun initListeners() {
        binding.next.setOnClickListener { viewModel.onApplyClick() }
    }

    companion object {

        fun screen(gameId: Int) = DialogScreen {
            SelectPlayersDialog().apply {
                arguments = bundleOf(SelectPlayersViewModel.GAME_ID_ARG to gameId)
            }
        }
    }
}