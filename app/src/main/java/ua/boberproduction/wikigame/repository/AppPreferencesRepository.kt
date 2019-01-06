package ua.boberproduction.wikigame.repository

import android.content.Context
import android.preference.PreferenceManager
import java.util.*

class AppPreferencesRepository(context: Context) : PreferencesRepository {

    companion object {
        private const val PREF_HIGH_SCORE = "high_score"
        private const val PREF_USER_LEVEL = "user_level"
    }


    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setTotalPoints(points: Int) = prefs.edit().putInt(PREF_HIGH_SCORE, points).apply()

    override fun getTotalPoints(): Int = prefs.getInt(PREF_HIGH_SCORE, 0)

    override fun getAppLocale(): Locale = Locale.forLanguageTag("en")

}