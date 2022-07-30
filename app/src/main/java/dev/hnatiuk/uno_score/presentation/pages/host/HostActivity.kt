package dev.hnatiuk.uno_score.presentation.pages.host

import androidx.activity.viewModels
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.LayoutInflate
import dev.hnatiuk.core.presentation.base.view.BaseActivity
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.ActivityHostBinding
import dev.hnatiuk.uno_score.presentation.application.UnoScoreApplication
import dev.hnatiuk.uno_score.presentation.navigation.Screens

@AndroidEntryPoint
class HostActivity : BaseActivity<ActivityHostBinding, HostViewModel, HostEvent>() {

    override val bindingFactory: LayoutInflate<ActivityHostBinding> = ActivityHostBinding::inflate

    override val viewModel by viewModels<HostViewModel>()

    override fun ActivityHostBinding.initUI() {
        (application as UnoScoreApplication)
            .cicerone
            .getNavigatorHolder()
            .setNavigator(AppNavigator(this@HostActivity, R.id.fragmentContainer))

        cicerone.router.newRootScreen(Screens.start())
    }

    val cicerone: Cicerone<Router> get() = (application as UnoScoreApplication).cicerone
}
