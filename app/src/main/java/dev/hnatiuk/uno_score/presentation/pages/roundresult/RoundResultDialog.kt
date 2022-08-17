package dev.hnatiuk.uno_score.presentation.pages.roundresult

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseBottomDialog
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.extensions.dpToPx
import dev.hnatiuk.core.presentation.navigation.DialogScreen
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.recyclerview.decorator.MarginItemDecoration
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.DialogRoundResultBinding
import dev.hnatiuk.uno_score.presentation.pages.roundresult.RoundResultViewModel.Companion.GAME_ID_ARG
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.playerRoundResultAdapterDelegate

@AndroidEntryPoint
class RoundResultDialog :
    BaseBottomDialog<DialogRoundResultBinding, RoundResultViewModel, RoundResultEvent>() {

    override val inflate: Inflate<DialogRoundResultBinding> = DialogRoundResultBinding::inflate

    override val viewModel by viewModels<RoundResultViewModel>()

    private val roundResultAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            playerRoundResultAdapterDelegate(
                onScoreChanged = viewModel::onPlayerScoreChanged,
                onDoneClick = viewModel::onApplyClick
            )
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            if (this is BottomSheetDialog) {
                behavior.skipCollapsed = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun DialogRoundResultBinding.initUI() {
        requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        initPlayerRoundResult()
        next.setOnClickListener { viewModel.onApplyClick() }
    }

    override fun RoundResultViewModel.observeViewModel() {
        roundResultAdapter.bind(viewLifecycleOwner, playerItems)
        round.observe(viewLifecycleOwner) {
            binding.title.text = getString(R.string.round_result_confirm_button, it)
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            RoundResultEvent.OnClose -> dismiss()
        }
    }


    private fun initPlayerRoundResult() = with(binding.playersRoundResult) {
        adapter = roundResultAdapter
        addItemDecoration(MarginItemDecoration(spaceSize = dpToPx(3), RecyclerView.VERTICAL))
    }

    companion object {

        fun screen(gameId: Int) = DialogScreen {
            RoundResultDialog().apply {
                arguments = bundleOf(GAME_ID_ARG to gameId)
            }
        }
    }
}