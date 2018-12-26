package com.artfulbits.fletch.util

import ua.boberproduction.wikigame.repository.PreferencesRepository
import java.util.*

class TestPreferenceRepository : PreferencesRepository {
    override fun setTotalPoints(points: Int) {}

    override fun getTotalPoints(): Int = 0

    override fun getAppLocale(): Locale = Locale.forLanguageTag("en")

    override fun getUserLevel(): Int = 1
}