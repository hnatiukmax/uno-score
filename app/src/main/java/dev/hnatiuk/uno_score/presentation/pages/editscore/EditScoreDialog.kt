package dev.hnatiuk.uno_score.presentation.pages.editscore

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseBottomDialog
import dev.hnatiuk.uno_score.databinding.DialogEditScoreBinding

@AndroidEntryPoint
class EditScoreDialog :
    BaseBottomDialog<DialogEditScoreBinding, EditScoreViewModel, EditScoreEvent>() {

    override val inflate: Inflate<DialogEditScoreBinding> = DialogEditScoreBinding::inflate

    override val viewModel by viewModels<EditScoreViewModel>()
}