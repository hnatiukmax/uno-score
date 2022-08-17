package dev.hnatiuk.uno_score.presentation.pages.editscore

import android.content.DialogInterface
import android.os.Parcelable
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseBottomDialog
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.extensions.doOnEditorAction
import dev.hnatiuk.core.presentation.navigation.DialogScreen
import dev.hnatiuk.core.presentation.utils.hideKeyboard
import dev.hnatiuk.core.presentation.utils.showKeyboardWithDelay
import dev.hnatiuk.uno_score.databinding.DialogEditFinalScoreBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditFinalScoreArgs(
    val isEditMode: Boolean,
    val gameId: Int? = null,
    val oldScore: Int? = null
) : Parcelable

@AndroidEntryPoint
class EditFinalScoreDialog :
    BaseBottomDialog<DialogEditFinalScoreBinding, EditFinalScoreViewModel, EditFinalScoreEvent>() {

    override val inflate: Inflate<DialogEditFinalScoreBinding> = DialogEditFinalScoreBinding::inflate

    override val viewModel by viewModels<EditFinalScoreViewModel>()

    override fun DialogEditFinalScoreBinding.initUI() {
        requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        requireContext().showKeyboardWithDelay(binding.newFinalScore)
        apply.setOnClickListener { viewModel.onApplyClick() }
        newFinalScore.doOnEditorAction(EditorInfo.IME_ACTION_DONE, viewModel::onApplyClick)
    }

    override fun EditFinalScoreViewModel.observeViewModel() {
        binding.newFinalScore.bind(viewLifecycleOwner, newFinalScore)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            EditFinalScoreEvent.OnClose -> dismiss()
            is EditFinalScoreEvent.OnCloseWithResult -> {
                setFragmentResult(NEW_SCORE_REQUEST_KEY, bundleOf(NEW_SCORE_ARG to event.score))
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        requireContext().hideKeyboard(binding.newFinalScore)
        super.onDismiss(dialog)
    }

    companion object {

        const val NEW_SCORE_REQUEST_KEY = "NEW_SCORE_REQUEST_KEY"
        const val NEW_SCORE_ARG = "NEW_SCORE_ARG"

        fun screen(isEditMode: Boolean, gameId: Int? = null, score: Int? = null) = DialogScreen {
            EditFinalScoreDialog().apply {
                arguments = bundleOf(
                    EditFinalScoreViewModel.EDIT_FINAL_SCORE_ARG to EditFinalScoreArgs(
                        isEditMode, gameId, score
                    )
                )
            }
        }
    }
}