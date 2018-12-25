package ua.boberproduction.wikigame.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ua.boberproduction.wikigame.mvvm.game.GameFragment
import ua.boberproduction.wikigame.mvvm.menu.MainMenuFragment
import ua.boberproduction.wikigame.mvvm.pregame.PregameFragment
import ua.boberproduction.wikigame.mvvm.postgame.ResultsFragment


@Module
abstract class FragmentsProvider {

    @ContributesAndroidInjector
    internal abstract fun provideMainMenuFragment(): MainMenuFragment

    @ContributesAndroidInjector
    internal abstract fun providePreGameFragment(): PregameFragment

    @ContributesAndroidInjector
    internal abstract fun provideGameFragment(): GameFragment

    @ContributesAndroidInjector
    internal abstract fun provideResultsFragment(): ResultsFragment
}