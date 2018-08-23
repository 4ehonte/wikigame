package ua.boberproduction.wikigame.util

import android.content.Context
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.models.Level

/**
 * Utility class containing app user level preferences and supporting methods.
 */
object Levels {

    fun getUserLevel(context: Context, points: Int): Level {
        val rank = getRankName(context, points)
        val icon = getRankIconResource(context, points)
        val level = getCurrentLevel(context, points)
        return Level(level, points, rank, icon)
    }

    /**
     * Returns a number of the user's level, according to given points.
     */
    fun getCurrentLevel(context: Context, points: Int): Int {
        val levelPoints = getLevelPoints(context)

        var level = 0
        while (points >= levelPoints[level + 1]) {
            level++
        }

        return level
    }

    fun getRankName(context: Context, points: Int): String {
        val currentLevel = getCurrentLevel(context, points)
        val ranks = context.resources.getStringArray(R.array.rank_names)

        return ranks[currentLevel - 1]
    }

    fun getRankIconResource(context: Context, points: Int): Int {
        val currentLevel = getCurrentLevel(context, points)
        val icons = context.resources.getIntArray(R.array.rank_icons)

        return icons[currentLevel - 1]
    }

    fun getPointsToNextLevel(context: Context, points: Int): Int {
        val level = getCurrentLevel(context, points)
        val levelPoints = getLevelPoints(context)

        val nextLevelPoints = levelPoints[level + 1]
        return nextLevelPoints - points
    }

    fun getLevelPoints(context: Context): IntArray {
        return context.resources.getIntArray(R.array.rank_points)
    }
}
