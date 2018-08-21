package ua.boberproduction.wikigame

import android.os.Bundle
import androidx.navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    var backPressListener: OnBackPressListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onBackPressed() {
        if (backPressListener?.onBackPressed() != true)
            super.onBackPressed()
    }
}

interface OnBackPressListener{
    /**
     * Returns true if back press was consumed.
     */
    fun onBackPressed(): Boolean
}
