package ua.boberproduction.wikigame

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import ua.boberproduction.wikigame.di.ViewModelFactory
import ua.boberproduction.wikigame.util.snackbar
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    inline fun <reified T : ViewModel> getViewModel(): T {
        return ViewModelProviders.of(activity!!, viewModelFactory).get(T::class.java)
    }

    fun showError(message: String) {
        view?.snackbar(message, colorResource = R.color.error_snackbar)
    }
}