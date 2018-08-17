package ua.boberproduction.wikigame.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import ua.boberproduction.wikigame.repository.AppPreferencesProvider
import ua.boberproduction.wikigame.repository.AppRepository
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.repository.network.WikiApi
import ua.boberproduction.wikigame.util.RxSchedulerProvider
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Provides
    @Singleton
    fun providesRepository(apiService: WikiApi): Repository = AppRepository(apiService)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun providePreferencesProvider(application: Application): PreferencesProvider = AppPreferencesProvider(application)
}