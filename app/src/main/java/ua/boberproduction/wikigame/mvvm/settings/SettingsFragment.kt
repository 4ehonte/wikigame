package ua.boberproduction.wikigame.mvvm.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {
    lateinit var binding: FragmentSettingsBinding
    lateinit var viewModel: SettingsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        binding.viewModel = viewModel

        back_button.setOnClickListener { activity?.onBackPressed() }

        pref_font_size_label.setOnClickListener { font_size_switch.isOn = !font_size_switch.isOn }
        pref_sounds_label.setOnClickListener { mute_switch.isOn = !mute_switch.isOn }

        font_size_switch.setOnToggledListener { _, isOn -> viewModel.onFontSizeToggled(isOn) }
        mute_switch.setOnToggledListener { _, isOn -> viewModel.onMuteToggled(isOn) }

        viewModel.resetScoreAlertDialog.observe(this, Observer {
            activity!!.alert(getString(R.string.reset_score_alert)) {
                positiveButton(getString(R.string.yes)) { viewModel.resetScores() }
                negativeButton(getString(R.string.no)) { }
            }.show()
        })

        viewModel.message.observe(this, Observer {
            activity!!.toast(it)
        })
    }
}