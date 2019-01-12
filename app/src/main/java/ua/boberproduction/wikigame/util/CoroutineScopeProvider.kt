package ua.boberproduction.wikigame.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineScopeProvider {
    fun main(): CoroutineScope
    fun io(): CoroutineScope
}

class AppCoroutineScopeProvider() : CoroutineScopeProvider {
    override fun main(): CoroutineScope = CoroutineScope(Dispatchers.Main)
    override fun io(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}