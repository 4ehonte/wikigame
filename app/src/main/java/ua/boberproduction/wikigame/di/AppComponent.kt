package ua.boberproduction.wikigame.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ua.boberproduction.wikigame.App
import ua.boberproduction.wikigame.di.modules.AppModule
import ua.boberproduction.wikigame.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class, NetworkModule::class, ActivityBuilder::class))
interface AppComponent: AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}