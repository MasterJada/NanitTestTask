package dev.jetlaunch.nanittesttask.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.jetlaunch.nanittesttask.databinding.GiftCardFragmentLayoutBindingImpl
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.gift_card_fragment_layout.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.io.File

class GiftCardFragment : Fragment() {
    companion object {
        val newInstance = GiftCardFragment()
    }

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindings = GiftCardFragmentLayoutBindingImpl.inflate(inflater)
        bindings.vm = viewModel
        bindings.lifecycleOwner = viewLifecycleOwner

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.shareGiftCardAction.observe(viewLifecycleOwner, Observer {
           val bmp =  generateCard()
            shareBitmap(saveBitmapToTempFile(bmp))
            print("")
        })
    }

    private fun generateCard(): Bitmap {
        bt_close.visibility = View.GONE
        bt_share.visibility = View.GONE
        bt_change_pic.visibility = View.GONE


        iv_baby_pic.invalidate()
        val bitmap = gift_card_layout.drawToBitmap()

        bt_close.visibility = View.VISIBLE
        bt_share.visibility = View.VISIBLE
        bt_change_pic.visibility = View.VISIBLE
        return bitmap
    }

    private fun shareBitmap(imageUri: Uri){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/*"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun saveBitmapToTempFile(bitmap: Bitmap): Uri {
        val dir = requireContext().cacheDir
        val file = File.createTempFile("nanit", ".jpg", dir)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, file.outputStream())
        return FileProvider.getUriForFile(requireContext(), "dev.jetlaunch.nanittesttask.fileprovider", file)
    }

}