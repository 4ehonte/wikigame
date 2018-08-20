package ua.boberproduction.wikigame.repository

import android.content.Context
import java.util.*

class AppPreferencesProvider(context: Context) : PreferencesProvider {

    override fun getAppLocale(): Locale {
        return Locale.forLanguageTag("en")
    }

    override fun getUserLevel(): Int {
        return 1
    }

}