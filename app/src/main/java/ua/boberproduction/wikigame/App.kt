package ua.boberproduction.wikigame

import android.webkit.WebSettings
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import ua.boberproduction.wikigame.di.DaggerAppComponent

val ioScope = CoroutineScope(Dispatchers.IO)
val uiScope = CoroutineScope(Dispatchers.Main)

class App : DaggerApplication(), HasActivityInjector {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        preloadWebView()
    }

    // Workaround to preload WebView native libraries in background to avoid UI lag when GameFragment is initialized
    private fun preloadWebView() {
        ioScope.launch { WebSettings.getDefaultUserAgent(applicationContext) }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}