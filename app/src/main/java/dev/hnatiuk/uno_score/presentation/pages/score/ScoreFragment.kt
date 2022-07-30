package dev.hnatiuk.uno_score.presentation.pages.score

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.uno_score.databinding.FragmentScoreBinding

@AndroidEntryPoint
class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel, ScoreEvent>() {

    override val inflate: Inflate<FragmentScoreBinding> = FragmentScoreBinding::inflate

    override val viewModel by viewModels<ScoreViewModel>()
}