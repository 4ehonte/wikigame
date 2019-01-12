package ua.boberproduction.wikigame.util

import ua.boberproduction.wikigame.repository.ResourcesRepository

class TestResourceRepository: ResourcesRepository {
    override fun getString(res: Int): String {
        return "test"
    }

    override fun getLevelPoints(): IntArray {
        return intArrayOf(0, 5, 10, 100, 1000)
    }

    override fun getRankIcons(): IntArray {
        return intArrayOf(0, 5, 10, 100, 1000)
    }

    override fun getRankNames(): Array<String> {
        return arrayOf("one", "two", "three", "four", "five")
    }
}