package com.artfulbits.fletch.util

import ua.boberproduction.wikigame.repository.PreferencesProvider

class TestPreferenceProvider : PreferencesProvider {
    override fun getUserLevel(): Int {
        return 1
    }
}