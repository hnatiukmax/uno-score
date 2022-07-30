package dev.hnatiuk.uno_score.presentation.pages.start

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.Inflate
import dev.hnatiuk.core.presentation.base.view.BaseFragment
import dev.hnatiuk.uno_score.databinding.FragmentStartBinding
import dev.hnatiuk.uno_score.presentation.navigation.Screens
import dev.hnatiuk.uno_score.presentation.pages.host.HostActivity

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel, StartEvent>() {

    override val inflate: Inflate<FragmentStartBinding> = FragmentStartBinding::inflate

    override val viewModel by viewModels<StartViewModel>()

    override fun FragmentStartBinding.initUI() {
        start.setOnClickListener {
            (activity as HostActivity)
                .cicerone
                .router
                .navigateTo(Screens.score())
        }
    }
}