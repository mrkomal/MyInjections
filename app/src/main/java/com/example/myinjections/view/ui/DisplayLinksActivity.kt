package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myinjections.R
import com.example.myinjections.view.adapters.UsefulLinksViewPagerAdapter
import com.example.myinjections.viewmodel.UsefulLinksViewModel
import kotlinx.android.synthetic.main.activity_display_links.*
import kotlinx.android.synthetic.main.usefullinks_viewpager_item.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.concurrent.schedule

class DisplayLinksActivity : AppCompatActivity() {

    private lateinit var usefulLinksViewModel: UsefulLinksViewModel
    private val viewPagerAdapter = UsefulLinksViewPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_links)

        // Setting toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.display_links_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // ViewModel
        usefulLinksViewModel = getViewModel()

        // Display progress bar until data is loaded
        GlobalScope.launch {
            // display progress bar
            progress_bar.isVisible = true

            // do nothing until page title appears
            while(page_title_textview == null){
                // page title is still null
            }

            // if first title appears then hide progress bar
            progress_bar.isVisible = false
        }

        // RecyclerView
        initializeRecyclerView()

        // Observers
        subscribeToObservers()
    }

    private fun initializeRecyclerView() {
        val viewPager: ViewPager2 = findViewById(R.id.links_viewpager)
        viewPager.adapter = viewPagerAdapter
    }

    private fun subscribeToObservers() {
        usefulLinksViewModel.allLinks.observe(this, Observer {
            viewPagerAdapter.submitList(it)
        })
    }
}