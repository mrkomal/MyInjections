package com.example.myinjections

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.view.InjectionsListAdapter
import com.example.myinjections.view.ui.AddInjectionActivity
import com.example.myinjections.viewmodel.InjectionsViewModel
import com.example.myinjections.viewmodel.InjectionsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var injectionsViewModel: InjectionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_injection_button.setOnClickListener {
            Intent(this, AddInjectionActivity::class.java).also {
                startActivity(it)
            }
        }

        ///////////////////// TEMP
        //Injection ViewModel
        injectionsViewModel = ViewModelProvider(
            this,
            InjectionsViewModelFactory(InjectionsRepository(application))
        )
            .get(InjectionsViewModel::class.java)
        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.temporary_recyclerview)
        // Adapter listy
        val adapter = InjectionsListAdapter()
        // Przypisujemy adapter do naszej listy
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //Funckja do dodawania nowych wpisow
        injectionsViewModel.insertInjectionInfo(InjectionInfo(0,"A","2020",0.2))
        //Observer
        injectionsViewModel.injectionsInfo.observe(this, Observer {
                injectionsInfos -> injectionsInfos.let {
            adapter.submitList(it)
        }
        })
        ////////////////////
    }
}