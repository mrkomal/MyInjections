package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myinjections.R
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_add_injection.*
import java.util.*

class AddInjectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

        setYearPickerValues()
        setDoseSliderLabel()
    }

    private fun setDoseSliderLabel(){
        dose_slider.setLabelFormatter { value: Float ->
            return@setLabelFormatter "$value ml"
        }
    }

    private fun setYearPickerValues(){
        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        val gapBetweenYears: Int = 120

        year_picker.maxValue = currentYear
        year_picker.minValue = currentYear - gapBetweenYears
    }
}