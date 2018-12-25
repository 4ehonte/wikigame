package ua.boberproduction.wikigame.repository

import android.app.Application
import ua.boberproduction.wikigame.R

class AppResRepository(private val app: Application) : ResourcesRepository {

    override fun getLevelPoints(): IntArray = app.resources.getIntArray(R.array.rank_points)

    override fun getRankIcons(): IntArray = app.resources.getIntArray(R.array.rank_icons)

    override fun getRankNames(): Array<String> = app.resources.getStringArray(R.array.rank_names)

}