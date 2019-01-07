package ua.boberproduction.wikigame.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ua.boberproduction.wikigame.mvvm.game.GameFragment
import ua.boberproduction.wikigame.mvvm.game.MenuDialogFragment
import ua.boberproduction.wikigame.mvvm.menu.MainMenuFragment
import ua.boberproduction.wikigame.mvvm.pregame.PregameFragment
import ua.boberproduction.wikigame.mvvm.postgame.ResultsFragment
import ua.boberproduction.wikigame.mvvm.pregame.InfoDialogFragment
import ua.boberproduction.wikigame.mvvm.settings.SettingsFragment
import ua.boberproduction.wikigame.mvvm.statistics.HistoryFragment
import ua.boberproduction.wikigame.mvvm.statistics.StatisticsFragment


@Module
abstract class FragmentsProvider {

    @ContributesAndroidInjector
    internal abstract fun provideMainMenuFragment(): MainMenuFragment

    @ContributesAndroidInjector
    internal abstract fun providePreGameFragment(): PregameFragment

    @ContributesAndroidInjector
    internal abstract fun provideInfoDialogFragment(): InfoDialogFragment

    @ContributesAndroidInjector
    internal abstract fun provideGameFragment(): GameFragment

    @ContributesAndroidInjector
    internal abstract fun provideResultsFragment(): ResultsFragment

    @ContributesAndroidInjector
    internal abstract fun provideStatisticsFragment(): StatisticsFragment

    @ContributesAndroidInjector
    internal abstract fun provideHistoryFragment(): HistoryFragment

    @ContributesAndroidInjector
    internal abstract fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    internal abstract fun provideMenuDialogFragment(): MenuDialogFragment
}