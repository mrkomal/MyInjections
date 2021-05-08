package com.example.myinjections.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TransportInfo
import android.os.Build
import androidx.lifecycle.*
import com.example.myinjections.network.model.Place
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.repository.usefullinks.UsefulLinksRepository
import com.example.myinjections.tools.InternetConnectionState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class UsefulLinksViewModel(private val app: Application,
                           private val internetConnectionState: InternetConnectionState,
                           private val repository: UsefulLinksRepository): AndroidViewModel(app) {

    init {
        if(getInternetConnection()){
            getAllLinks()
        }
    }

    private val _allLinks = MutableLiveData<List<UsefulLink>>()
    val allLinks: LiveData<List<UsefulLink>>
        get() = _allLinks


    fun getAllLinks() = viewModelScope.launch {
        repository.getAllLinks()
            .collect { _allLinks.value = it }
    }

    private fun getInternetConnection(): Boolean {
        return internetConnectionState.hasInternetConnection(app)
    }
}