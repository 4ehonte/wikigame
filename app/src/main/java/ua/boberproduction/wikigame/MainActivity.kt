package ua.boberproduction.wikigame

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), NavController.OnDestinationChangedListener {
    @Inject
    lateinit var dataRepository: DataRepository
    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    var backPressListener: OnBackPressListener? = null
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(text_scroller_bkg) {
            val level = preferencesRepository.getUserLevel()

            // get phrases from repository to display them randomly on background
            disposables.add(
                    dataRepository.getPhrases(level)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe { phrasesList -> phrases = phrasesList })

            textColorResource = R.color.floating_text_color
            fontResource = R.font.linux_libertine
        }

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onBackPressed() {
        if (backPressListener?.onBackPressed() != true)
            super.onBackPressed()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.gameFragment) {
            text_scroller_bkg.visibility = View.GONE
        } else
            text_scroller_bkg.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}

interface OnBackPressListener {
    /**
     * Returns true if back press was consumed.
     */
    fun onBackPressed(): Boolean
}
