package ua.boberproduction.wikigame.repository

import android.content.Context

class AppPreferencesProvider(context: Context) : PreferencesProvider {
    override fun getUserLevel(): Int {
        return 1
    }

}