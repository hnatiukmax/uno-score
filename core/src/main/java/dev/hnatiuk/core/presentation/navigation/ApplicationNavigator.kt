package dev.hnatiuk.core.presentation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator

open class ApplicationNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : AppNavigator(activity, containerId, fragmentManager, fragmentFactory) {

    override fun applyCommand(command: Command) {
        when (command) {
            is CloseDialog -> closeDialog(command)
            is ShowDialog -> showDialog(command)
            else -> super.applyCommand(command)
        }
    }

    protected open fun showDialog(command: ShowDialog) {
        val tag = command.screen.screenKey
        val dialog = command.screen.createDialog(fragmentFactory)
        dialog.show(fragmentManager, tag)
    }

    protected open fun closeDialog(command: CloseDialog) {
        val tag = command.screen.screenKey
        val dialog = fragmentManager.findFragmentByTag(tag)
        if (dialog is DialogFragment) {
            dialog.dismiss()
        }
    }
}