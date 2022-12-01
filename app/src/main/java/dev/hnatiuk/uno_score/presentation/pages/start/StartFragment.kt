package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.core.presentation.binding.bind
import dev.hnatiuk.core.presentation.extensions.dpToPx
import dev.hnatiuk.core.presentation.recyclerview.AsyncListDiffDelegationAdapter
import dev.hnatiuk.core.presentation.recyclerview.decorator.GridItemDecoration
import dev.hnatiuk.core.presentation.recyclerview.layoutmanager.SpannableGridLayoutManager
import dev.hnatiuk.core.presentation.recyclerview.layoutmanager.SpannableGridLayoutManager.GridSpanLookup
import dev.hnatiuk.core.presentation.recyclerview.layoutmanager.SpannableGridLayoutManager.SpanInfo
import dev.hnatiuk.uno_score.databinding.FragmentStartBinding
import dev.hnatiuk.uno_score.domain.entity.Score
import dev.hnatiuk.uno_score.presentation.pages.base.inputdialog.InputDialog.Companion.setFragmentSingleResultListener
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.customLabelScoreSuggestionAdapterDelegate
import dev.hnatiuk.uno_score.presentation.recyclerview.adapter.finalScoreSuggestionItemAdapterDelegate

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel, StartEvent>() {

    override val inflate: Inflate<FragmentStartBinding> = FragmentStartBinding::inflate

    override val viewModel by viewModels<StartViewModel>()

    private val finalScoreSuggestionsAdapter by lazy {
        AsyncListDiffDelegationAdapter(
            finalScoreSuggestionItemAdapterDelegate { viewModel.onFinalScoreSelected(it.score) },
            customLabelScoreSuggestionAdapterDelegate(viewModel::onCustomFinalScoreSelected)
        )
    }

    override fun FragmentStartBinding.initUI() {
        version.bind(viewLifecycleOwner, viewModel.version)
        initFinalScoreSuggestions()
        setupFragmentResultListeners()
    }

    override fun StartViewModel.observeViewModel() {
        finalScoreSuggestionsAdapter.bind(viewLifecycleOwner, finalScoreSuggestionsData)
    }

    private fun initFinalScoreSuggestions() = with(binding) {
        finalScoreSuggestions.adapter = finalScoreSuggestionsAdapter
        finalScoreSuggestions.layoutManager =
            SpannableGridLayoutManager(spanLookup = object : GridSpanLookup {
                override fun getSpanInfo(position: Int): SpanInfo {
                    return when (position) {
                        0 -> SpanInfo(5, 5)
                        1 -> SpanInfo(3, 3)
                        2 -> SpanInfo(2, 3)
                        3 -> SpanInfo(2, 2)
                        4 -> SpanInfo(3, 2)
                        else -> SpanInfo(0, 0)
                    }
                }
            }, columns = 10, cellAspectRatio = 1f)
        finalScoreSuggestions.addItemDecoration(GridItemDecoration(dpToPx(2)))
    }

    private fun setupFragmentResultListeners() {
        setFragmentSingleResultListener(StartViewModel.CUSTOM_FINAL_SCORE_REQUEST_KEY) { value ->
            val score = value.toIntOrNull() ?: return@setFragmentSingleResultListener
            viewModel.onFinalScoreSelected(Score(score))
        }
    }

    companion object {

        fun screen() = FragmentScreen {
            StartFragment()
        }
    }
}