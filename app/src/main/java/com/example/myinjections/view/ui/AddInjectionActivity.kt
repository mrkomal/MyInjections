package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myinjections.R
import kotlinx.android.synthetic.main.activity_add_injection.*
import java.util.*

class AddInjectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

        setYearPickerValues()
    }

    private fun setYearPickerValues(){
        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        val gapBetweenYears: Int = 120

        year_picker.maxValue = currentYear
        year_picker.minValue = currentYear - gapBetweenYears
    }
}