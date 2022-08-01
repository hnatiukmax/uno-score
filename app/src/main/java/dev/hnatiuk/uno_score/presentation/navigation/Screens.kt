package dev.hnatiuk.uno_score.presentation.navigation;

import com.github.terrakok.cicerone.androidx.FragmentScreen
import dev.hnatiuk.core.presentation.navigation.DialogScreen
import dev.hnatiuk.uno_score.presentation.pages.editscore.EditScoreDialog
import dev.hnatiuk.uno_score.presentation.pages.score.ScoreFragment
import dev.hnatiuk.uno_score.presentation.pages.start.StartFragment

object Screens {

    fun start() = FragmentScreen {
        StartFragment()
    }

    fun score() = FragmentScreen {
        ScoreFragment()
    }
    
    fun editScore() = DialogScreen {
        EditScoreDialog()
    }
}