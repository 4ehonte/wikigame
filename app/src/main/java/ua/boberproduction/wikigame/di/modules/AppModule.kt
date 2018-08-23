package ua.boberproduction.wikigame.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ua.boberproduction.wikigame.repository.AppPreferencesProvider
import ua.boberproduction.wikigame.repository.AppRepository
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.repository.local.GameDataBase
import ua.boberproduction.wikigame.repository.local.ResultsDao
import ua.boberproduction.wikigame.repository.network.WikiApi
import ua.boberproduction.wikigame.util.RxSchedulerProvider
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Provides
    @Singleton
    fun providesRepository(apiService: WikiApi, dataBase: GameDataBase): Repository = AppRepository(apiService, dataBase)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun providePreferencesProvider(application: Application): PreferencesProvider = AppPreferencesProvider(application)

    @Provides
    @Singleton
    fun provideDb(application: Application): GameDataBase {
        return Room
                .databaseBuilder(application, GameDataBase::class.java, "gamedb.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideResultsDao(db: GameDataBase): ResultsDao = db.resultsDao()
}