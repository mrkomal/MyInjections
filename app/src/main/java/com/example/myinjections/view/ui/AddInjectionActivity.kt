package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myinjections.R
import kotlinx.android.synthetic.main.activity_add_injection.*

class AddInjectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

        year_picker.minValue = 1900
        year_picker.maxValue = 2020
    }
}