package ua.boberproduction.wikigame.util

import java.util.*

/**
 * Returns a randomItem element using the specified [random] instance as the source of randomness.
 */
fun <E> List<E>.randomItem(random: java.util.Random): E? = if (size > 0) get(random.nextInt(size)) else null


/**
 * Returns random integer in given range.
 */
fun Random.int(min: Int, max: Int): Int {
    if (min >= max) throw IllegalArgumentException("Max must be greater than min")
    return nextInt((max - min) + 1) + min
}