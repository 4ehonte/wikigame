package ua.boberproduction.wikigame.util

import ua.boberproduction.wikigame.repository.PreferencesRepository
import java.util.*

class TestPreferenceRepository : PreferencesRepository {
    override fun setTotalPoints(points: Int) {}

    override fun getTotalPoints(): Int = 0

    override fun getAppLocale(): Locale = Locale.forLanguageTag("en")

    override fun setSoundEnabled(isEnabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSoundEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFontSize(fontSize: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFontZoom(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}