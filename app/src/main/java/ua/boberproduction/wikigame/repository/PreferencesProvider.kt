package ua.boberproduction.wikigame.repository

import java.util.*

interface PreferencesProvider {
    fun getUserLevel(): Int
    fun getAppLocale(): Locale
}