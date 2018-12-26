package ua.boberproduction.wikigame.repository

import io.reactivex.functions.Function

class ResourceMapper<T> : Function<Resource<T>, T> {

    override fun apply(resource: Resource<T>): T {
        when (resource) {
            is Resource.Success -> return resource.data
            is Resource.Failure -> throw resource.throwable
                    ?: throw Error("Unknown resource failure")
        }
    }
}