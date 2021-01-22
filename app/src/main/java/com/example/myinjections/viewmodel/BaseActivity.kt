package com.example.myinjections.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myinjections.viewmodel.InjectionsViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

open class BaseActivity : AppCompatActivity() {

    lateinit var injectionsViewModel: InjectionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectionsViewModel = getViewModel()
    }
}