package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myinjections.R

class DisplayLinksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_links)

        //Setting toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.display_links_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}