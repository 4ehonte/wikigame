package ua.boberproduction.wikigame.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ua.boberproduction.wikigame.repository.*
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
    fun providesDataRepository(apiService: WikiApi, dataBase: GameDataBase, application: Application): DataRepository = AppDataRepository(apiService, dataBase, application)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun providePreferencesRepository(application: Application): PreferencesRepository = AppPreferencesRepository(application)

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

    @Provides
    @Singleton
    fun providesResourceRepository(application: Application): ResourcesRepository = AppResRepository(application)
}