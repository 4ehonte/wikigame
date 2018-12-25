package ua.boberproduction.wikigame.util.views

import android.app.PendingIntent.getActivity
import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import at.grabner.circleprogress.AnimationState
import at.grabner.circleprogress.AnimationStateChangedListener
import at.grabner.circleprogress.CircleProgressView
import ua.boberproduction.wikigame.util.Levels
import java.io.File
import java.io.IOException

class CircularLevelView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null) : CircleProgressView(context, attributeSet) {

    var levels: Levels? = null
    var listener: EventListener? = null
    var startPoints: Int = 0
    var newPoints: Int = 0

    fun startAnimation() {
        showLevelProgress(startPoints, newPoints)
    }

    fun showLevelProgress(pointsBefore: Int, pointsNow: Int) {
        val currentLevel = levels!!.getUserLevel(pointsBefore).levelNumber
        val currentLevelPoints = levels!!.getLevelStartingPoints(currentLevel)
        val nextLevelPoints = levels!!.getPointsToNextLevel(currentLevel)

        // show the current level number
        setText(currentLevel.toString())

        maxValue = (nextLevelPoints - currentLevelPoints).toFloat()

        // set LinearInterpolator if circularBar is going to fill up to 100%, and default interpolator if not
        if (pointsNow < nextLevelPoints) {
            setValueInterpolator(DecelerateInterpolator())
        } else {
            setValueInterpolator(LinearInterpolator())
        }

        // launch animation again recursively if bar reached 100%
        setOnAnimationStateChangedListener(object : AnimationStateChangedListener {
            private var animationStarted = false

            override fun onAnimationStateChanged(_animationState: AnimationState) {
                when (_animationState) {
                    AnimationState.ANIMATING -> animationStarted = true
                    AnimationState.IDLE -> if (animationStarted) {
                        animationStarted = false
                        if (currentValue >= maxValue) {
                            if (pointsNow - currentLevelPoints >= maxValue) {
                                showLevelProgress(nextLevelPoints, pointsNow)
                            }
                        }
                    }
                }
            }
        })

        setValueAnimated((pointsBefore - currentLevelPoints).toFloat(), Math.min(pointsNow - currentLevelPoints, nextLevelPoints - currentLevelPoints).toFloat(), 1500)
    }

    init {
    }

    private fun setInitialValue(points: Int) {
        val level = levels!!.getUserLevel(points).levelNumber
        setText(level.toString())
        val previousLevel = if (level > 0) level - 1 else 0
        val levelStartingPoints = levels!!.getPointsToNextLevel(previousLevel)
        val levelEndingPoints = levels!!.getPointsToNextLevel(level)

        maxValue = (levelEndingPoints - levelStartingPoints).toFloat()

        val startValue = (points - levelStartingPoints).toFloat()
        setValue(startValue)
    }

    private fun setNewValue(points: Int) {
        val level = levels!!.getUserLevel(startPoints).levelNumber
        val previousLevel = if (level > 0) level - 1 else 0
        val levelStartingPoints = levels!!.getPointsToNextLevel(previousLevel)

        val max = maxValue
        val targetValue = points - levelStartingPoints
        if (targetValue > maxValue)
            setValueAnimated(maxValue)
        else
            setValueAnimated(targetValue.toFloat())
    }

    interface EventListener {
        fun onLevelUp(level: Int)
    }
}