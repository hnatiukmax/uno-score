package dev.hnatiuk.core.presentation.base.view;

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.navigation.ApplicationNavigator

abstract class BaseHostActivity<VB : ViewBinding, VM : BaseViewModel<E>, E : Event> : BaseActivity<VB, VM, E>() {

    abstract var navigationHolder: NavigatorHolder
    abstract val containerId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        navigationHolder.setNavigator(ApplicationNavigator(this@BaseHostActivity, containerId))
    }
}