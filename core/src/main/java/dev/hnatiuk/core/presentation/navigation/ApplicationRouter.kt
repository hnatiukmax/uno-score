package dev.hnatiuk.core.presentation.navigation

import com.github.terrakok.cicerone.Router

class ApplicationRouter : Router() {

    fun showDialog(screen: DialogScreen) = executeCommands(ShowDialog(screen))

    fun closeDialog(screen: DialogScreen) = executeCommands(CloseDialog(screen))
}