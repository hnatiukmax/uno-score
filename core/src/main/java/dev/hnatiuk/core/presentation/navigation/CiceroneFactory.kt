package dev.hnatiuk.core.presentation.navigation;

import com.github.terrakok.cicerone.Cicerone

class CiceroneFactory {

    companion object {

        fun create() = Cicerone.create(ApplicationRouter())
    }
}