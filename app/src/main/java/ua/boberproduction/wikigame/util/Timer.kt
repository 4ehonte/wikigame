package ua.boberproduction.wikigame.util

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Observable timer, emitting seconds up or down.
 * Can be paused, isResumed and isStopped.
 * Timer pauses when it reaches zero while counting down (or when [.stop] is called).
 */
class Timer : Flowable<Long>() {
    var time: Long = 0
    private val isResumed = AtomicBoolean(true)
    private val isStopped = AtomicBoolean(false)
    private var flowable: Flowable<Long>? = null

    init {
        initializeFlowable()
    }

    fun isRunning() = isResumed.get() && !isStopped.get()

    private fun initializeFlowable() {
        flowable = Flowable.interval(1, TimeUnit.SECONDS, Schedulers.computation())
                .takeWhile { !isStopped.get() }
                .filter { isResumed.get() }
                .map { ++time }
    }

    /**
     * Timer stops emitting seconds until [.resume] is called.
     */
    fun pause() {
        isResumed.set(false)
    }

    /**
     * Get time in human readable format (mm:ss)
     */
    fun getFormattedTime(): String {
        val minutes = time / 60
        val secondsLeft = time % 60
        return String.format("%02d:%02d", minutes, secondsLeft)
    }

    fun resume() {
        isResumed.set(true)
    }

    /**
     * Stop emitting seconds. This operation is irreversible, the timer cannot be started again.
     */
    fun stop() {
        isStopped.set(true)
    }

    fun addSeconds(seconds: Int) {
        time += seconds.toLong()
    }

    fun reset() {
        time = 0
    }

    override fun subscribeActual(s: Subscriber<in Long>) {
        flowable!!.subscribe(s)
    }
}