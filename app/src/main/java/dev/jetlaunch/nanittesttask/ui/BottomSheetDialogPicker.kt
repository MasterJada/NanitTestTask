package dev.jetlaunch.nanittesttask.ui

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.jetlaunch.nanittesttask.R
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.bottom_sheet_dialog_picker_layout.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetDialogPicker : BottomSheetDialogFragment() {
    companion object {
        val newInstance = BottomSheetDialogPicker()
    }

    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog_picker_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_camera.setOnClickListener(::buttonClick)
        bt_folder.setOnClickListener(::buttonClick)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isAdded)
            super.show(manager, tag)
    }

    private fun buttonClick(v: View) {
        when (v.id) {
            R.id.bt_camera -> {
                registerForActivityResult(ActivityResultContracts.RequestPermission()){
                   if(it)
                       startCamera()
                }.launch(Manifest.permission.CAMERA)

            }
            R.id.bt_folder -> {
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){map ->
                    if(map.all { it.value }) startPicker()
                }.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))

            }
        }

    }

    private fun startCamera() {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File.createTempFile("nanit_${timeStamp}", ".jpg", requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        val uri = FileProvider.getUriForFile(requireContext(), "dev.jetlaunch.nanittesttask.fileprovider", file)
        registerForActivityResult(ActivityResultContracts.TakePicture()){
            if(it){
                val bmp = BitmapFactory.decodeFile(file.absolutePath)
                viewModel.pickedImage.postValue(bmp)
            }
            dismiss()
        }.launch(uri)
    }

    private fun startPicker(){
        registerForActivityResult(ActivityResultContracts.GetContent()){
            val bmp = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,it)
           viewModel.pickedImage.postValue(bmp)
            dismiss()
        }.launch("image/*")
    }

}