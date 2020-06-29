package dev.jetlaunch.nanittesttask.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.*
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

@BindingAdapter("android:date")
fun setDate(picker: DatePicker, date: Date?) {
    val calendar = Calendar.getInstance()
    calendar.time = date ?: Date()
    picker.updateDate(
        calendar.get(Calendar.YEAR),
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
}

@InverseBindingAdapter(attribute = "android:date")
fun getDate(datePicker: DatePicker): Date {
    val calendar = Calendar.getInstance()
    calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth, 0, 0, 0)
    return calendar.time
}

@BindingAdapter(value = ["android:onDateChanged", "android:dateAttrChanged"], requireAll = false)
fun setListeners(
    datePicker: DatePicker,
    dateChangedListener: DatePicker.OnDateChangedListener?,
    inverseBindingListener: InverseBindingListener?
) {
    val newListener = if (inverseBindingListener == null) dateChangedListener
    else DatePicker.OnDateChangedListener { _, _, _, _ -> inverseBindingListener.onChange() }
    datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth, newListener)
}

@BindingAdapter("imageBitmap")
fun setBitmap(imageView: ShapeableImageView, bmp: Bitmap?) {
    if (bmp == null) return
    imageView.setImageBitmap(bmp)
}
@BindingAdapter("backgroundImageResource")
fun setBackgroundResource(view: View, resID: Int){
    view.setBackgroundResource(resID)
}

@BindingAdapter("setError")
fun setError(layout: TextInputLayout, error: String?) {
    if (error.isNullOrEmpty()) {
        layout.isErrorEnabled = false
        layout.error = ""
    } else {
        layout.isErrorEnabled = true
        layout.error = error
    }
}

@BindingAdapter("imageResource")
fun setImageResource(imageView: AppCompatImageView, resID: Int) {
    imageView.setImageResource(resID)
}





