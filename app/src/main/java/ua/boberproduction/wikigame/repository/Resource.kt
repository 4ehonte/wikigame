package ua.boberproduction.wikigame.repository

/**
 * A generic class that holds a value with its loading status.
 **/
sealed class Resource<out T> {
//    class Loading<out T> : Resource<T>() {
//        override fun toString(): String = "Resource: Loading..."
//    }
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable? = null) : Resource<T>()
}