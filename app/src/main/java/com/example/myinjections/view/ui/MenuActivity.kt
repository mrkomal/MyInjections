package com.example.myinjections.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myinjections.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //setting action bar text
        supportActionBar?.title = "MENU"

        // Click listeners for menu buttons
        display_injections_button.setOnClickListener(this)
        add_injection_button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {

            R.id.display_injections_button -> {
                Intent(this, DisplayInjectionActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.add_injection_button -> {
                Intent(this, AddInjectionActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

}