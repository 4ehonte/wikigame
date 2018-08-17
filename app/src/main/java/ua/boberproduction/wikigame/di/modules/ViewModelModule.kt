package ua.boberproduction.wikigame.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ua.boberproduction.wikigame.di.ViewModelFactory
import ua.boberproduction.wikigame.di.ViewModelKey
import ua.boberproduction.wikigame.ui.menu.MainMenuViewModel
import ua.boberproduction.wikigame.ui.pregame.PregameViewModel


@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    internal abstract fun bindMainMenuViewModel(mainMenuViewModel: MainMenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PregameViewModel::class)
    internal abstract fun bindPregameViewModel(pregameViewModel: PregameViewModel): ViewModel
}