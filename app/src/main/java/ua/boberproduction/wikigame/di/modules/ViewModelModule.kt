package ua.boberproduction.wikigame.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ua.boberproduction.wikigame.di.ViewModelFactory
import ua.boberproduction.wikigame.di.ViewModelKey
import ua.boberproduction.wikigame.mvvm.game.GameViewModel
import ua.boberproduction.wikigame.mvvm.menu.MainMenuViewModel
import ua.boberproduction.wikigame.mvvm.pregame.PregameViewModel
import ua.boberproduction.wikigame.mvvm.postgame.ResultsViewModel


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

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    internal abstract fun bindGameViewModel(gameViewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultsViewModel::class)
    internal abstract fun bindResultsViewModel(resultsViewModel: ResultsViewModel): ViewModel
}