package dev.hnatiuk.uno_score.data.repositories

import android.app.UiModeManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.hnatiuk.uno_score.domain.repository.ApplicationSettings
import javax.inject.Inject

class ApplicationSettingsImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : ApplicationSettings {

    private val uiManager by lazy {
        applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    }

    override fun setTheme(mode: Int) {
        uiManager.setApplicationNightMode(mode)
    }
}