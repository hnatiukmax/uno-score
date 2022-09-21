package dev.hnatiuk.uno_score.presentation.pages.base

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseBottomDialog
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.navigation.DialogScreen
import dev.hnatiuk.uno_score.databinding.DialogTwoActionBinding
import dev.hnatiuk.uno_score.presentation.pages.base.TwoActionDialogEvent.OnCloseWithCancelResult
import dev.hnatiuk.uno_score.presentation.pages.base.TwoActionDialogEvent.OnCloseWithConfirmResult
import dev.hnatiuk.uno_score.presentation.pages.base.TwoActionDialogViewModel.Companion.TWO_ACTION_DIALOG_ARG
import kotlinx.parcelize.Parcelize

@Parcelize
data class TwoActionDialogArgs(
    val description: String,
    val confirmButtonText: String,
    val cancelButtonText: String,
    val payload: Parcelable? = null
) : Parcelable

@AndroidEntryPoint
class TwoActionDialog :
    BaseBottomDialog<DialogTwoActionBinding, TwoActionDialogViewModel, TwoActionDialogEvent>() {

    override val inflate: Inflate<DialogTwoActionBinding> = DialogTwoActionBinding::inflate

    override val viewModel by viewModels<TwoActionDialogViewModel>()

    override fun DialogTwoActionBinding.initUI() {
        confirm.setOnClickListener { viewModel.onConfirmClick() }
        cancel.setOnClickListener { viewModel.onCancelClick() }
    }

    override fun TwoActionDialogViewModel.observeViewModel() {
        binding.description.bind(viewLifecycleOwner, description)
        binding.confirm.bind(viewLifecycleOwner, confirmButtonText)
        binding.cancel.bind(viewLifecycleOwner, cancelButtonText)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is OnCloseWithConfirmResult -> {
                setFragmentResult(
                    TWO_ACTION_DIALOG_REQUEST_KEY, bundleOf(
                        TWO_ACTION_DIALOG_PAYLOAD_ARG to event.payload
                    )
                )
                dismiss()
            }
            OnCloseWithCancelResult -> dismiss()
        }
    }

    companion object {

        const val TWO_ACTION_DIALOG_REQUEST_KEY = "TWO_ACTION_DIALOG_REQUEST_KEY"
        const val TWO_ACTION_DIALOG_PAYLOAD_ARG = "TWO_ACTION_DIALOG_PAYLOAD_ARG"

        fun screen(
            description: String,
            confirmButtonText: String,
            cancelButtonText: String
        ) = DialogScreen {
            TwoActionDialog().apply {
                arguments = bundleOf(
                    TWO_ACTION_DIALOG_ARG to TwoActionDialogArgs(
                        description, confirmButtonText, cancelButtonText
                    )
                )
            }
        }
    }
}