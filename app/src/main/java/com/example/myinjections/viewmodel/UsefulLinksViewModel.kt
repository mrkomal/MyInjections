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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class UsefulLinksViewModel(val app: Application, private val repository: UsefulLinksRepository):
    AndroidViewModel(app) {

    init {
        if(hasInternetConnection()){
            getAllLinks()
        }
    }

    private val _allLinks = MutableLiveData<List<UsefulLink>>()
    val allLinks: LiveData<List<UsefulLink>>
        get() = _allLinks


    private fun getAllLinks() = viewModelScope.launch {
        repository.getAllLinks()
            .collect { _allLinks.value = it }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}