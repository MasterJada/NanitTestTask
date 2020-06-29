package dev.jetlaunch.nanittesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import dev.jetlaunch.nanittesttask.ui.BottomSheetDialogPicker
import dev.jetlaunch.nanittesttask.ui.DetailsFragment
import dev.jetlaunch.nanittesttask.ui.GiftCardFragment
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            supportFragmentManager.commit {
                replace(R.id.container, DetailsFragment.newInstance)
            }

        viewModel.moveToBirthdayScreenAction.observe(this, Observer {
            supportFragmentManager.commit {
                replace(R.id.container, GiftCardFragment.newInstance)
                addToBackStack("gift_card")
            }
        })

        viewModel.closeGiftCardAction.observe(this, Observer {
            supportFragmentManager.popBackStack()
        })

        viewModel.startImagePickerAction.observe(this, Observer {
            BottomSheetDialogPicker.newInstance.show(supportFragmentManager, "BOTTOM_DIALOG")
        })
    }

}