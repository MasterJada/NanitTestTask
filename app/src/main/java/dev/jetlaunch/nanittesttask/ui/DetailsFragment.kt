package dev.jetlaunch.nanittesttask.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dev.jetlaunch.nanittesttask.databinding.DetailsFragmentLayoutBinding
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel



class DetailsFragment : Fragment() {
    companion object {
        val newInstance = DetailsFragment()
    }

    private val vm: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindings = DetailsFragmentLayoutBinding.inflate(inflater)
        bindings.vm = vm
        bindings.lifecycleOwner = viewLifecycleOwner

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.startImagePicker.observe(viewLifecycleOwner, Observer {
            BottomSheetDialogPicker.newInstance.show(parentFragmentManager, "BOTTOM_DIALOG")
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        BottomSheetDialogPicker.newInstance.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}