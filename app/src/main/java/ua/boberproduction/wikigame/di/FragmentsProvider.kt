package ua.boberproduction.wikigame.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ua.boberproduction.wikigame.ui.game.GameFragment
import ua.boberproduction.wikigame.ui.menu.MainMenuFragment
import ua.boberproduction.wikigame.ui.pregame.PregameFragment
import ua.boberproduction.wikigame.ui.results.ResultsFragment


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