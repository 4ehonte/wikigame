package ua.boberproduction.wikigame.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TestCoroutineScopeProvider : CoroutineScopeProvider {
    override fun main(): CoroutineScope = CoroutineScope(Dispatchers.Unconfined)

    override fun io(): CoroutineScope = CoroutineScope(Dispatchers.Unconfined)
}