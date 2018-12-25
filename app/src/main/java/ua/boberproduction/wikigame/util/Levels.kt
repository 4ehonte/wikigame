package ua.boberproduction.wikigame.util

import ua.boberproduction.wikigame.models.Level
import ua.boberproduction.wikigame.repository.ResourcesRepository

/**
 * Utility class containing app user level preferences and supporting methods.
 */
class Levels(private val resourcesRepository: ResourcesRepository) {

    fun getUserLevel(points: Int): Level {
        val rank = getRankName(points)
        val icon = getRankIconResource(points)
        val level = getCurrentLevel(points)
        return Level(level, points, rank, icon)
    }

    /**
     * Returns a number of the user's level, according to given points.
     */
    fun getCurrentLevel(points: Int): Int {
        val levelPoints = resourcesRepository.getLevelPoints()

        var level = 0
        while (points >= levelPoints[level]) {
            level++
        }

        return level
    }

    fun getRankName(points: Int): String {
        val currentLevel = getCurrentLevel(points)
        val ranks = resourcesRepository.getRankNames()

        return ranks[currentLevel - 1]
    }

    fun getRankIconResource(points: Int): Int {
        val currentLevel = getCurrentLevel(points)
        val icons = resourcesRepository.getRankIcons()

        return icons[currentLevel - 1]
    }

    fun getPointsToNextLevel(level: Int): Int {
        val levelPoints = resourcesRepository.getLevelPoints()
        return levelPoints[level]
    }

    fun getLevelStartingPoints(level: Int): Int {
        val previousLevel = if (level > 0) level - 1 else 0
        val levelPoints = resourcesRepository.getLevelPoints()
        return levelPoints[previousLevel]
    }
}
