package com.artfulbits.fletch.util

import ua.boberproduction.wikigame.repository.PreferencesProvider
import java.util.*

class TestPreferenceProvider : PreferencesProvider {
    override fun getAppLocale(): Locale {
        return Locale.forLanguageTag("en")
    }

    override fun getUserLevel(): Int {
        return 1
    }
}