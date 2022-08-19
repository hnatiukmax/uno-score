package dev.hnatiuk.uno_score.presentation.pages.host

import android.app.UiModeManager
import androidx.activity.viewModels
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.hilt.android.AndroidEntryPoint
import dev.hnatiuk.core.presentation.base.LayoutInflate
import dev.hnatiuk.core.presentation.base.view.BaseHostActivity
import dev.hnatiuk.uno_score.R
import dev.hnatiuk.uno_score.databinding.ActivityHostBinding
import dev.hnatiuk.uno_score.domain.repository.ApplicationSettings
import javax.inject.Inject

@AndroidEntryPoint
class HostActivity : BaseHostActivity<ActivityHostBinding, HostViewModel, HostEvent>() {

    override val bindingFactory: LayoutInflate<ActivityHostBinding> = ActivityHostBinding::inflate

    override val viewModel by viewModels<HostViewModel>()

    @Inject
    override lateinit var navigationHolder: NavigatorHolder

    @Inject
    lateinit var settings: ApplicationSettings

    override val containerId = R.id.fragmentContainer

    override fun ActivityHostBinding.initUI() {
        settings.setTheme(UiModeManager.MODE_NIGHT_NO)
        viewModel.launch()
    }
}
