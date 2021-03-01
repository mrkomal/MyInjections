package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.myinjections.R
import com.example.myinjections.view.adapters.UsefulLinksViewPagerAdapter
import com.example.myinjections.viewmodel.UsefulLinksViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

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