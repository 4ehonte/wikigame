package ua.boberproduction.wikigame

import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import ua.boberproduction.wikigame.di.DaggerAppComponent

class App : DaggerApplication(), HasActivityInjector {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}