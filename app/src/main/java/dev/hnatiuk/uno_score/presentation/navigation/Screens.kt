package dev.hnatiuk.uno_score.presentation.navigation;

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.hnatiuk.uno_score.presentation.pages.score.ScoreFragment
import dev.hnatiuk.uno_score.presentation.pages.start.StartFragment

object Screens {

    fun start() = FragmentScreen {
        StartFragment()
    }

    fun score() = FragmentScreen {
        ScoreFragment()
    }
}