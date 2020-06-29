package dev.jetlaunch.nanittesttask.core

import android.content.Context
import dev.jetlaunch.nanittesttask.R
import dev.jetlaunch.nanittesttask.model.BabyData
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.util.*
import java.util.concurrent.TimeUnit

class BabyDataProcessor(private val context: Context) {

    fun getBabyData(birthday: Date, name: String): BabyData {
        val text = String.format(context.resources.getString(R.string.baby_name, name))
        val pos = Random().nextInt(3)
        return BabyData(
            text,
            dateToMonthPic(birthday),
            getBackgroundResource(pos),
            getBackgroundColor(pos),
            getPlaceHolderResource(pos),
            getCameraIconResource(pos)
        )
    }

    private fun dateToMonthPic(date: Date): Int {
        val start = DateTime(date.time)
        val end = DateTime.now()
        var calcResult = Months.monthsBetween(start, end).months
        if (calcResult == 1) {
            val daysBetween = Days.daysBetween(start, end).days
            if(daysBetween > 15) calcResult = -120
        }

        return when (calcResult) {
            0 -> R.mipmap.m0
            1 -> R.mipmap.m1
            -120 -> R.mipmap.m1_half
            2 -> R.mipmap.m2
            3 -> R.mipmap.m3
            4 -> R.mipmap.m4
            5 -> R.mipmap.m5
            6 -> R.mipmap.m6
            7 -> R.mipmap.m7
            8 -> R.mipmap.m8
            9 -> R.mipmap.m9
            10 -> R.mipmap.m10
            11 -> R.mipmap.m11
            else -> R.mipmap.m12
        }
    }

    private fun getBackgroundResource(pos: Int): Int {
        return when (pos) {
            0 -> R.mipmap.android_fox_popup
            1 -> R.mipmap.android_elephant_popup
            else -> R.mipmap.android_pelican_popup
        }
    }

    private fun getPlaceHolderResource(pos: Int): Int {
        return when (pos) {
            0 -> R.mipmap.default_place_holder_green
            1 -> R.mipmap.default_place_holder_yellow
            else -> R.mipmap.default_place_holder_blue
        }
    }

    private fun getCameraIconResource(pos: Int): Int {
        return when (pos) {
            0 -> R.mipmap.camera_icon_green
            1 -> R.mipmap.camera_icon_yellow
            else -> R.mipmap.camera_icon_blue
        }
    }

    private fun getBackgroundColor(pos: Int): Int {
        return when (pos) {
            0 -> R.color.foxyGreen
            1 -> R.color.elephantYellow
            else -> R.color.pelicanBlue
        }
    }

}