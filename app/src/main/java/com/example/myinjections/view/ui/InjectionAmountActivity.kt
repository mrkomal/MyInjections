package com.example.myinjections.view.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.myinjections.R
import com.example.myinjections.viewmodel.InjectionAmountViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class InjectionAmountActivity : AppCompatActivity() {

    private lateinit var injectionAmountViewModel: InjectionAmountViewModel
    private lateinit var injectionImageView: ImageView
    private lateinit var injectionDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_injection_amount)

        // Setting toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.display_links_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        injectionImageView = findViewById(R.id.injection_picture)
        injectionDate = findViewById(R.id.last_injection_date)

        // ViewModel
        injectionAmountViewModel = getViewModel()

        // set current picture and val
        setImageViewImageResource()
        setLastInjectionDate()

        // update picture when image is pressed
        injectionImageView.setOnClickListener {
            injectionAmountViewModel.updateIconOnClick()
            setImageViewImageResource()
            setLastInjectionDate()
        }
    }

    private fun setImageViewImageResource(){
        injectionImageView.setImageResource(injectionAmountViewModel.imageToDisplay)
    }

    private fun setLastInjectionDate(){
        injectionDate.text = injectionAmountViewModel.lastAppUpdateFromSharedPref ?: ""
    }
}