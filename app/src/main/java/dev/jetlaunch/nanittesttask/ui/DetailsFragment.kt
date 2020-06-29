package dev.jetlaunch.nanittesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.jetlaunch.nanittesttask.databinding.DetailsFragmentLayoutBinding
import dev.jetlaunch.nanittesttask.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.details_fragment_layout.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*


class DetailsFragment : Fragment() {
    companion object {
        val newInstance = DetailsFragment()
    }

    private val vm: MainViewModel by sharedViewModel()

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
        datePicker.maxDate = Date().time

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.add(Calendar.MONTH, -12)

        datePicker.minDate = minDateCalendar.timeInMillis
    }
}