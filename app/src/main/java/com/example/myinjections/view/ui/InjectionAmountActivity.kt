package com.example.myinjections.view.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myinjections.R

class InjectionAmountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_injection_amount)

        val a = findViewById<ImageView>(R.id.injection_picture)
        a.setOnClickListener {
            a.setImageResource(R.drawable.pic_injection_state_1)
        }
    }
}