package ua.boberproduction.wikigame.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ua.boberproduction.wikigame.MainActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(FragmentsProvider::class)])
    abstract fun bindMainActivity(): MainActivity
}