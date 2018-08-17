package ua.boberproduction.wikigame.di.modules

import dagger.Module
import dagger.Provides
import ua.boberproduction.wikigame.util.RxSchedulerProvider
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class  AppModule {

//    @Provides
//    @Singleton
//    fun providesRepository(apiService: WikiApi): Repository = MainRepository(apiService)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

//    @Provides
//    @Singleton
//    fun providePreferencesProvider(application: Application): PreferencesProvider = GamePreferencesProvider(application)
}