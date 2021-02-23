package com.example.myinjections.view.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myinjections.R
import com.example.myinjections.view.adapters.InjectionsListAdapter
import com.example.myinjections.viewmodel.InjectionsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DisplayInjectionActivity : AppCompatActivity() {

    private val adapter = InjectionsListAdapter()
    lateinit var injectionsViewModel: InjectionsViewModel

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

        // ViewModel
        injectionsViewModel = getViewModel()

        // Setup RecyclerView
        initializeRecyclerView()

        // Creating observer for InjectionsViewModel data
        subscribeToObservers()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)

        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                injectionsViewModel.filterInjectionInfo(newText ?: "")
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.sort_by_name_button -> {
                injectionsViewModel.sortInjectionsInfoByName()
                true
            }
            R.id.sort_by_year -> {
                injectionsViewModel.sortInjectionsInfoByYear()
                true
            }
            R.id.sort_by_dose_button -> {
                injectionsViewModel.sortInjectionInfoByDose()
                true
            }
            R.id.show_only_obligatory_button -> {
                injectionsViewModel.showObligatoryInjectionInfo()
                true
            }
            R.id.show_only_optional_button -> {
                injectionsViewModel.showOptionalInjectionInfo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun subscribeToObservers() {
        injectionsViewModel.resultInjectionInformation.observe(this, Observer { injectionsInfos ->
            injectionsInfos.let { adapter.submitList(it) }
        })
    }


    private fun initializeRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.injections_recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }


    private val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val chosenInjectionInfo = adapter.currentList[position]
            injectionsViewModel.deleteInjectionInfo(chosenInjectionInfo)

            Snackbar.make(
                findViewById(R.id.display_injection_layout),
                "${chosenInjectionInfo.name} ${getString(R.string.snackbar_info)}",
                Snackbar.LENGTH_LONG)
                .show()
        }
    }
}