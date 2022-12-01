package dev.hnatiuk.uno_score.presentation.navigation

import android.os.Parcelable
import android.text.InputType
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.core.presentation.utils.StringResource
import dev.hnatiuk.core.presentation.utils.asStringRes
import dev.hnatiuk.uno_score.R.string
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialog
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialogArgs

fun ApplicationRouter.showSetFinalScoreInputDialog(
    requestKey: String,
    currentFinalScore: Int? = null
) = showInputDialog(
    requestKey = requestKey,
    title = string.set_final_score_title.asStringRes,
    inputHint = string.set_final_score_input_hint.asStringRes,
    inputDefaultValue = currentFinalScore?.toString().orEmpty(),
    inputType = InputType.TYPE_CLASS_NUMBER,
    positiveButton = string.set_final_score_positive_button.asStringRes
)

fun ApplicationRouter.showAddNewPlayerInputDialog(requestKey: String) = showInputDialog(
    requestKey = requestKey,
    title = string.add_new_player_title.asStringRes,
    inputHint = string.add_new_player_input_hint.asStringRes,
    inputType = InputType.TYPE_CLASS_TEXT,
    positiveButton = string.add_new_player_positive_button.asStringRes
)


private fun ApplicationRouter.showInputDialog(
    requestKey: String,
    title: StringResource,
    inputHint: StringResource,
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    inputDefaultValue: String = "",
    positiveButton: StringResource,
    payload: Parcelable? = null
) {
    showDialog(
        InputDialog.screen(
            InputDialogArgs(
                requestKey,
                title,
                inputHint,
                inputType,
                inputDefaultValue,
                positiveButton,
                payload
            )
        )
    )
}