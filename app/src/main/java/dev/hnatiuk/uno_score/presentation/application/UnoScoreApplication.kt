package dev.hnatiuk.uno_score.presentation.application

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UnoScoreApplication : Application() {

    val cicerone = Cicerone.create()
}