package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.uno_score.databinding.FragmentStartBinding

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel, StartEvent>() {

    override val inflate: Inflate<FragmentStartBinding> = FragmentStartBinding::inflate

    override val viewModel by viewModels<StartViewModel>()

    override fun FragmentStartBinding.initUI() {
        start.setOnClickListener {
            viewModel.onStartClick()
        }
    }

    override fun FragmentStartBinding.bind() = with(viewModel) {
        finalScore.bind(viewLifecycleOwner, finalScoreData, ::onFinalScoreChanged)
    }
}