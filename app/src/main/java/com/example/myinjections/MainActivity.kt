package com.example.myinjections

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    companion object{
        const val OBTAINED_DATA_TAG = "reply_intent_received"
    }

    private lateinit var injectionsViewModel: InjectionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_injection_button.setOnClickListener {
            Intent(this, AddInjectionActivity::class.java).also {
                startActivityForResult(it, AddInjectionActivity.ACTIVITY_REQUEST_CODE)
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
        //Observer
        injectionsViewModel.injectionsInfo.observe(this, Observer {
                injectionsInfos -> injectionsInfos.let {
            adapter.submitList(it)
        }
        })
        ////////////////////
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == AddInjectionActivity.ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(OBTAINED_DATA_TAG, "Data from new injection has arrived.")

            // unpack data
            val injectionName = data!!.getStringExtra(AddInjectionActivity.nameKey).toString()
            val injectionYear = data.getStringExtra(AddInjectionActivity.yearKey).toString()
            val injectionDose = data.getStringExtra(AddInjectionActivity.doseKey)!!.toDouble()
            val injectionObligatory = data.getStringExtra(AddInjectionActivity.isObligatoryKey).toString()

            // create new info instance and push it to database
            val newInjectionInfo = InjectionInfo(0, injectionName, injectionYear, injectionDose)
            injectionsViewModel.insertInjectionInfo(newInjectionInfo)
            Log.d(OBTAINED_DATA_TAG, "Data passed to database.")
        }
    }
}