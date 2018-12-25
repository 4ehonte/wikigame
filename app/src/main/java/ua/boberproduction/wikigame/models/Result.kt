package ua.boberproduction.wikigame.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(val level: Int,
                  val startPhrase: String,
                  val targetPhrase: String,
                  val clicks: Int,
                  val seconds: Int,
                  val date: Long) : Parcelable {

    companion object {
        const val MAX_LEVEL_POINTS_COEF = 1000
    }

    /**
     * Calculate the number of points earned. Points depend on level complexity, number of clicks spent to reach the target,
     * and consumed time.
     */
    fun getPoints(): Int {
        // 40% of points are assigned for time
        val timeCoef = seconds * 30
        var timePoints = (level * MAX_LEVEL_POINTS_COEF * 0.4).toInt() - timeCoef
        if (timePoints < 0) timePoints = 0

        // another 60% - for number of clicks
        val clickPoints = (level * MAX_LEVEL_POINTS_COEF * 0.6).toInt() / clicks

        return timePoints + clickPoints
    }
}