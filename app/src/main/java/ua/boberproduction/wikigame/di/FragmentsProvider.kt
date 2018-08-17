package ua.boberproduction.wikigame.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ua.boberproduction.wikigame.ui.menu.MainMenuFragment
import ua.boberproduction.wikigame.ui.pregame.PregameFragment


@Module
abstract class FragmentsProvider {

    @ContributesAndroidInjector
    internal abstract fun provideMainMenuFragment(): MainMenuFragment

    @ContributesAndroidInjector
    internal abstract fun providePreGameFragment(): PregameFragment
}