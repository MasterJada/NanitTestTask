package dev.jetlaunch.nanittesttask.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.jetlaunch.nanittesttask.core.BabyDataProcessor
import dev.jetlaunch.nanittesttask.model.BabyData
import dev.jetlaunch.nanittesttask.utils.SingleLiveEvent
import java.util.*

class MainViewModel(private val babyDataProcessor: BabyDataProcessor): ViewModel() {
    val babyName = MutableLiveData<String>()
    val birthdayDate = MutableLiveData(Date())
    val pickedImage = MutableLiveData<Bitmap>()
    val babyData = MutableLiveData<BabyData>()

    val nameError = Transformations.map(babyName){
        if(it.isEmpty()) "Name can't bee empty" else ""
    }

    val startImagePickerAction = SingleLiveEvent<Void>()
    val moveToBirthdayScreenAction = SingleLiveEvent<Void>()
    val closeGiftCardAction = SingleLiveEvent<Void>()
    val shareGiftCardAction = SingleLiveEvent<Void>()



    fun validateDataAndMoveToBirthdayScreen(){
        val date = birthdayDate.value ?: return
        val name = if(!babyName.value.isNullOrEmpty()){
            babyName.value
        }else{
            babyName.postValue("")
            return
        }
        babyData.postValue(babyDataProcessor.getBabyData(date, name!!))
        moveToBirthdayScreenAction.call()
    }

    fun closeGiftCard(){
        closeGiftCardAction.call()
    }

    fun pickImage(){
        startImagePickerAction.call()
    }

    fun share(){
        shareGiftCardAction.call()
    }
}