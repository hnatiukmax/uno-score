package dev.hnatiuk.uno_score.presentation.pages.score

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.extensions.toast
import dev.hnatiuk.uno_score.databinding.FragmentScoreBinding

@AndroidEntryPoint
class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel, ScoreEvent>() {

    override val inflate: Inflate<FragmentScoreBinding> = FragmentScoreBinding::inflate

    override val viewModel by viewModels<ScoreViewModel>()

    private val score: Int by lazy { requireArguments().getInt(SCORE_ARG) }

    override fun FragmentScoreBinding.initUI() {
        toast(this@ScoreFragment.score.toString())
    }

    companion object {

        fun screen(score: Int) = FragmentScreen {
            ScoreFragment().apply {
                arguments = bundleOf(SCORE_ARG to score)
            }
        }

        private const val SCORE_ARG = "SCORE_ARG"
    }
}