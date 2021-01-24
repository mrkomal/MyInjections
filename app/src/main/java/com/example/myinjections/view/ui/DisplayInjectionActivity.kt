package com.example.myinjections.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        //Setting action bar title
        supportActionBar?.title = "MY INJECTIONS"

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.injections_recyclerview)

        // List Adapter
        val adapter = InjectionsListAdapter()

        // Binding list adapter with RV
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Creating observer for InjectionsViewModel data
        injectionsViewModel.injectionsInfo.observe(this, Observer { injectionsInfos ->
            injectionsInfos.let { adapter.submitList(it) }
        })

    }
}