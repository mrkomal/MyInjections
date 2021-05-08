package com.example.myinjections.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.example.myinjections.network.model.Place
import com.example.myinjections.repository.places.PlacesRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.*
import com.example.myinjections.R
import com.example.myinjections.tools.InternetConnectionState
import com.example.myinjections.tools.InternetConnectionStateImpl
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Duration

class PlacesViewModel(private val app: Application,
                      private val internetConnectionState: InternetConnectionState,
                      private val placesRepository: PlacesRepository) : AndroidViewModel(app) {

    companion object {
        // Default radius set to 5 km
        const val NEAREST_PLACES_RADIUS: Double = 5.0
        // Main Square in Cracow as default Location
        val DEFAULT_LOCATION = LatLng(50.06177188776352, 19.93736629340411)
        val PHARMACY_ICON_ID = R.drawable.baseline_local_pharmacy_black_18dp
        val CLINIC_ICON_ID = R.drawable.baseline_medical_services_black_24dp
    }

    private var _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>>
        get() = _places

    var lastKnownLocation: Location? = null

    var isUnwrapButtonClicked = false

    var currentPlaceTypeIcon: Int = CLINIC_ICON_ID

    fun getNearestClinics() {
        if(getInternetConnection()){
            val flow = placesRepository.getAllClinics()
            currentPlaceTypeIcon = CLINIC_ICON_ID
            filterPlacesFlowAndSetLiveData(flow)
        } else {
            Toast
                .makeText(app.applicationContext, "No internet connection.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun getNearestPharmacies() {
        if(getInternetConnection()) {
            val flow = placesRepository.getAllPharmacies()
            currentPlaceTypeIcon = PHARMACY_ICON_ID
            filterPlacesFlowAndSetLiveData(flow)
        } else {
            Toast
                .makeText(app.applicationContext, "No internet connection.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun getNearestPlaces() {
        if(getInternetConnection()) {
            val flow = placesRepository.getAllPlaces()
            filterPlacesFlowAndSetLiveData(flow)
        }
    }

    private fun filterPlacesFlowAndSetLiveData(flowList: Flow<List<Place>>)= viewModelScope.launch {
        flowList.map { list ->
            list.filter {
                val distance = calculateDistanceBetweenTwoLocationsInKm(
                    LatLng(
                        lastKnownLocation?.latitude ?: DEFAULT_LOCATION.latitude,
                        lastKnownLocation?.longitude ?: DEFAULT_LOCATION.longitude
                    ),
                    LatLng(it.latitude, it.longitude))
                distance < NEAREST_PLACES_RADIUS
            }
        }.collect { _places.value = it }
    }


    fun calculateDistanceBetweenTwoLocationsInKm(from: LatLng, to: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(from, to) / 1000
    }


    fun getInternetConnection(): Boolean {
        return internetConnectionState.hasInternetConnection(app)
    }
}