package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myinjections.R
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_add_injection.*
import java.util.*

class AddInjectionActivity : AppCompatActivity() {

    companion object{
        //UI items tags
        const val INSERT_BUTTON_TAG = "insert_button_pressed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

        //UI items settings
        setYearPickerValues()
        setDoseSliderLabel()

        //insert button pressed
        insert_button.setOnClickListener {
            Log.d(INSERT_BUTTON_TAG, "Insert button was pressed.")
        }
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