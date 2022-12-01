package dev.hnatiuk.uno_score.presentation.pages.base.inputdialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseBottomDialog
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.extensions.asStringLiveData
import dev.hnatiuk.core.presentation.extensions.doOnEditorAction
import dev.hnatiuk.core.presentation.navigation.DialogScreen
import dev.hnatiuk.core.presentation.utils.StringResource
import dev.hnatiuk.core.presentation.utils.hideKeyboard
import dev.hnatiuk.core.presentation.utils.showKeyboardWithDelay
import dev.hnatiuk.uno_score.databinding.DialogInputBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputDialogArgs(
    val requestKey: String,
    val title: StringResource,
    val inputHint: StringResource,
    val inputType: Int = InputType.TYPE_CLASS_TEXT,
    val inputDefaultValue: String = "",
    val positiveButton: StringResource,
    val payload: Parcelable? = null
) : Parcelable

@AndroidEntryPoint
class InputDialog :
    BaseBottomDialog<DialogInputBinding, InputDialogViewModel, InputDialogEvent>() {

    override val inflate: Inflate<DialogInputBinding> = DialogInputBinding::inflate

    override val viewModel by viewModels<InputDialogViewModel>()

    override fun DialogInputBinding.initUI() {
        requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        requireContext().showKeyboardWithDelay(binding.input)
        apply.setOnClickListener { viewModel.onApplyClick() }
        input.inputType = viewModel.args.inputType
        input.doOnEditorAction(EditorInfo.IME_ACTION_DONE, viewModel::onApplyClick)
    }

    override fun InputDialogViewModel.observeViewModel() {
        binding.input.bind(viewLifecycleOwner, inputValue)
        binding.title.bind(viewLifecycleOwner, title.asStringLiveData(requireContext()))
        binding.apply.bind(viewLifecycleOwner, confirmButtonText.asStringLiveData(requireContext()))
        inputHint.asStringLiveData(requireContext())
            .observe(viewLifecycleOwner, binding.input::setHint)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is InputDialogEvent.OnCloseWithResult -> {
                setFragmentResult(
                    event.requestKey, bundleOf(
                        INPUT_VALUE_ARG to event.value,
                        PAYLOAD_ARG to event.payload
                    )
                )
                requireContext().hideKeyboard(binding.input)
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        requireContext().hideKeyboard(binding.input)
        super.onDismiss(dialog)
    }

    companion object {

        const val INPUT_VALUE_ARG = "NEW_SCORE_ARG"
        const val PAYLOAD_ARG = "PAYLOAD_ARG"

        fun screen(args: InputDialogArgs) = DialogScreen {
            InputDialog().apply {
                arguments = bundleOf(
                    InputDialogViewModel.INPUT_DIALOG_ARG to args
                )
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <P : Parcelable> Fragment.setFragmentPayloadResultListener(
            requestKey: String,
            listener: (String, P?) -> Unit
        ) {
            parentFragmentManager.setFragmentResultListener(requestKey, this) { _: String, bundle: Bundle ->
                val value = bundle.getString(INPUT_VALUE_ARG) ?: throw IllegalArgumentException("No argument for argName")
                val payload = bundle.getParcelable<P>(PAYLOAD_ARG)
                listener.invoke(value, payload)
            }
        }

        fun Fragment.setFragmentSingleResultListener(
            requestKey: String,
            listener: (String) -> Unit
        ) {
            parentFragmentManager.setFragmentResultListener(requestKey, this) { _: String, bundle: Bundle ->
                val value = bundle.getString(INPUT_VALUE_ARG) ?: throw IllegalArgumentException("No argument for")
                listener.invoke(value)
            }
        }
    }
}