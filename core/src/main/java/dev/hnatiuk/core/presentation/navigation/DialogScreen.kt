package dev.hnatiuk.core.presentation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class DialogScreen(
    key: String? = null,
    override val clearContainer: Boolean = true,
    private val dialogCreator: Creator<FragmentFactory, DialogFragment>
) : FragmentScreen {

    override val screenKey: String = key ?: dialogCreator::class.java.name

    override fun createFragment(factory: FragmentFactory) = dialogCreator.create(factory)

    fun createDialog(factory: FragmentFactory): DialogFragment {
        return dialogCreator.create(factory)
    }
}