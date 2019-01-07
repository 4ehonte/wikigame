package ua.boberproduction.wikigame.repository

import android.content.Context
import android.preference.PreferenceManager
import java.util.*

class AppPreferencesRepository(context: Context) : PreferencesRepository {

    companion object {
        private const val PREF_HIGH_SCORE = "high_score"
        private const val PREF_SOUND_ENABLED = "sound enabled"
        private const val PREF_FONT_SIZE = "font size"
    }


    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setTotalPoints(points: Int) = prefs.edit().putInt(PREF_HIGH_SCORE, points).apply()

    override fun getTotalPoints(): Int = prefs.getInt(PREF_HIGH_SCORE, 0)

    override fun getAppLocale(): Locale = Locale.forLanguageTag("en")

    override fun setSoundEnabled(isEnabled: Boolean) = prefs.edit().putBoolean(PREF_SOUND_ENABLED, isEnabled).apply()

    override fun getSoundEnabled(): Boolean = prefs.getBoolean(PREF_SOUND_ENABLED, true)

    override fun setFontSize(fontSize: Int) = prefs.edit().putInt(PREF_FONT_SIZE, fontSize).apply()

    override fun getFontSize(): Int = prefs.getInt(PREF_FONT_SIZE, 12)
}