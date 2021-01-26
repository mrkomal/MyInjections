package com.example.myinjections.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.R
import com.example.myinjections.view.adapters.InjectionsListAdapter
import com.example.myinjections.viewmodel.BaseActivity

class DisplayInjectionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_injection)

        //Setting toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.display_injections_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.injections_recyclerview)

        // List Adapter
        val adapter = InjectionsListAdapter()

        // Binding list adapter with RV
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Creating observer for InjectionsViewModel data
        injectionsViewModel.resultInjectionInformation.observe(this, Observer { injectionsInfos ->
            injectionsInfos.let { adapter.submitList(it) }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.sort_by_name_button -> {
                injectionsViewModel.sortInjectionsInfoByName()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}