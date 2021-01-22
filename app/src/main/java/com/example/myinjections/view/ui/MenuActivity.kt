package com.example.myinjections

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.view.adapters.InjectionsListAdapter
import com.example.myinjections.view.ui.AddInjectionActivity
import com.example.myinjections.viewmodel.BaseActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        add_injection_button.setOnClickListener {
            Intent(this, AddInjectionActivity::class.java).also {
                startActivity(it)
            }
        }

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


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == AddInjectionActivity.ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            Log.d(OBTAINED_DATA_TAG, "Data from new injection has arrived.")
//
//            // unpack data
//            val injectionName = data!!.getStringExtra(AddInjectionActivity.nameKey).toString()
//            val injectionYear = data.getStringExtra(AddInjectionActivity.yearKey).toString()
//            val injectionDose = data.getStringExtra(AddInjectionActivity.doseKey)!!.toDouble()
//            val injectionObligatory = data.getStringExtra(AddInjectionActivity.isObligatoryKey).toString()
//            val illnessInformation = data.getStringExtra(AddInjectionActivity.illnessKey).toString()
//
//            // create new info instance and push it to database
//            val newInjectionInfo = InjectionInfo(0, injectionName, injectionYear,
//                injectionDose, injectionObligatory, illnessInformation)
//            injectionsViewModel.insertInjectionInfo(newInjectionInfo)
//            Log.d(OBTAINED_DATA_TAG, "Data passed to database.")
//        }
//    }
}