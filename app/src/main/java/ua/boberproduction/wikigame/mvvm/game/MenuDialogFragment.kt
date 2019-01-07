package ua.boberproduction.wikigame.mvvm.game

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_menu_dialog.*
import ua.boberproduction.wikigame.BaseDialogFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentMenuDialogBinding
import ua.boberproduction.wikigame.di.ViewModelFactory
import javax.inject.Inject


class MenuDialogFragment : BaseDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentMenuDialogBinding
    private lateinit var phrase: String

    companion object {
        const val TAG = "menu dialog fragment"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(GameViewModel::class.java)

        binding.viewModel = viewModel

        btn_return_to_game.setOnClickListener {
            viewModel.onMenuClosed()
        }

        btn_main_menu.setOnClickListener {
            viewModel.onMainMenuBtnClicked()
        }

        viewModel.showMenu.observe(this, Observer {
            if (it == false) dismiss()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_dialog, container, false)
        binding.setLifecycleOwner(this)

        isCancelable = true

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        viewModel.onMenuClosed()
    }
}