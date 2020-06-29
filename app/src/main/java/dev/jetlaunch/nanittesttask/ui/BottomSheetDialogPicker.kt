package dev.jetlaunch.nanittesttask.ui

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.jetlaunch.nanittesttask.R
import dev.jetlaunch.nanittesttask.utils.Constants
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.bottom_sheet_dialog_picker_layout.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetDialogPicker : BottomSheetDialogFragment() {
    companion object {
        val newInstance = BottomSheetDialogPicker()
    }

    private val viewModel: MainViewModel by sharedViewModel()


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
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    if (it)
                        startCamera()
                }.launch(Manifest.permission.CAMERA)

            }
            R.id.bt_folder -> {
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                    if (map.all { it.value }) startPicker()
                }.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )

            }
        }

    }

    private fun startCamera() {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File.createTempFile(
            "nanit_${timeStamp}",
            ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val uri = FileProvider.getUriForFile(
            requireContext(),
            Constants.AUTHORITY,
            file
        )
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                val bmp = BitmapFactory.decodeFile(file.absolutePath)
                val exif = ExifInterface(file.absolutePath)

                viewModel.pickedImage.postValue(processBitmap(bmp, exif))
            }
            dismiss()
        }.launch(uri)
    }

    private fun startPicker() {
        registerForActivityResult(ActivityResultContracts.GetContent()) {uri ->
            uri?.let {
                val bmp = fromUriToBitmap(it)
                viewModel.pickedImage.postValue(bmp)
            }
            dismiss()
        }.launch("image/*")
    }

    private fun fromUriToBitmap(uri: Uri): Bitmap? {
        requireContext().contentResolver.openInputStream(uri)?.use { stream ->
            val bmp = BitmapFactory.decodeStream(stream)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val exif = ExifInterface(stream)
                return processBitmap(bmp, exif)
            }
        }
        return null
    }

    private fun processBitmap(bmp: Bitmap, exif: ExifInterface): Bitmap {
        val rotate = when (exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            else -> 0
        }
        val matrix = Matrix()
        matrix.postRotate(rotate.toFloat())
        return Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
    }

}