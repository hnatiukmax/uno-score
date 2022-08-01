package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.extensions.dpToPx
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.recyclerview.decorator.MarginItemDecoration
import dev.hnatiuk.uno_score.databinding.FragmentStartBinding
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.finalScoreAdapterDelegate

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel, StartEvent>() {

    override val inflate: Inflate<FragmentStartBinding> = FragmentStartBinding::inflate

    override val viewModel by viewModels<StartViewModel>()

    private val finalScoreSuggestionsAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            finalScoreAdapterDelegate(viewModel::onFinalScoreSelected)
        )
    }

    override fun FragmentStartBinding.initUI() {
        initFinalScoreSuggestions()
        start.setOnClickListener { viewModel.onStartClick() }
        scoreDown.setOnClickListener { viewModel.onScoreDown() }
        scoreUp.setOnClickListener { viewModel.onScoreUp() }
    }

    override fun FragmentStartBinding.bind(): Unit = with(viewModel) {
        finalScore.bind(viewLifecycleOwner, finalScoreData)
        finalScoreSuggestionsAdapter.subscribe(viewLifecycleOwner, finalScoreSuggestionsData)
    }

    private fun initFinalScoreSuggestions() = with(binding) {
        finalScoreSuggestions.adapter = finalScoreSuggestionsAdapter
        finalScoreSuggestions.addItemDecoration(
            MarginItemDecoration(
                dpToPx(4),
                RecyclerView.HORIZONTAL
            )
        )
    }
}