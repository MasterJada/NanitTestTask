package dev.jetlaunch.nanittesttask.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.jetlaunch.nanittesttask.utils.SingleLiveEvent
import java.util.*

class MainViewModel: ViewModel() {
    val babyName = MutableLiveData<String>()
    val birthdayDate = MutableLiveData(Date())
    val startImagePicker = SingleLiveEvent<Void>()
    val pickedImage = MutableLiveData<Bitmap>()

    fun pickImage(){
        startImagePicker.call()
    }
}