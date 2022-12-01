package dev.hnatiuk.uno_score.presentation.pages.players

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.binding.bindVisibility
import dev.hnatiuk.core.presentation.extensions.addDivider
import dev.hnatiuk.core.presentation.extensions.doOnEditorAction
import dev.hnatiuk.core.presentation.extensions.reverse
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.utils.SimplePopupMenu.Companion.showPopupMenu
import dev.hnatiuk.core.presentation.utils.hideKeyboard
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.FragmentPlayersBinding
import dev.hnatiuk.uno_score.domain.entity.Player
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialog.Companion.setFragmentPayloadResultListener
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.playerAdapterDelegate

@AndroidEntryPoint
class PlayersFragment : BaseFragment<FragmentPlayersBinding, PlayersViewModel, PlayersEvent>() {

    override val inflate: Inflate<FragmentPlayersBinding> = FragmentPlayersBinding::inflate

    override val viewModel by viewModels<PlayersViewModel>()

    private val playersAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            playerAdapterDelegate(::onPlayerClick)
        )
    }

    override fun FragmentPlayersBinding.initUI() {
        initPlayers()
        initListener()
        setupFragmentResultListeners()
    }

    override fun PlayersViewModel.observeViewModel() {
        binding.playersEmpty.bindVisibility(viewLifecycleOwner, isEmptyState)
        binding.players.bindVisibility(viewLifecycleOwner, isEmptyState.reverse())
        binding.newPlayerName.bind(viewLifecycleOwner, newPlayerName, newPlayerName::setValue)
        playersAdapter.bind(viewLifecycleOwner, players)
    }

    private fun initPlayers() = with(binding) {
        players.adapter = playersAdapter
        players.addDivider(R.dimen.margin_divider_player_item, RecyclerView.VERTICAL)
    }

    private fun initListener() = with(binding) {
        addPlayer.setOnClickListener { viewModel.onAddPlayerClick() }
        newPlayerName.doOnEditorAction(EditorInfo.IME_ACTION_DONE) {
            requireContext().hideKeyboard(newPlayerName)
            viewModel.onAddPlayerClick()
        }
    }

    private fun onPlayerClick(player: Player, view: View) {
        view.showPopupMenu(R.menu.menu_player) { viewModel.handlePlayerMenu(it, player) }
    }

    private fun setupFragmentResultListeners() {
        setFragmentPayloadResultListener(
            PlayersViewModel.LOSE_COUNT_REQUEST_KEY,
            viewModel::onSetLoseCount
        )
    }

    companion object {

        fun screen() = FragmentScreen {
            PlayersFragment()
        }
    }
}