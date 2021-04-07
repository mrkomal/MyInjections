package com.example.myinjections.viewmodel

import android.location.Location
import com.example.myinjections.network.model.Place
import com.example.myinjections.repository.places.PlacesRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlacesViewModel(private val placesRepository: PlacesRepository) : ViewModel() {

    companion object {
        const val NEAREST_PLACES_RADIUS: Double = 5.0
        val DEFAULT_LOCATION = LatLng(-34.0, 151.0)
    }

    private var _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>>
        get() = _places

    var lastKnownLocation: Location? = null

    var isUnwrapButtonClicked = false

    fun getNearestClinics() {
        val flow = placesRepository.getAllClinics()
        filterPlacesFlowAndSetLiveData(flow)
    }

    fun getNearestPharmacies() {
        val flow = placesRepository.getAllPharmacies()
        filterPlacesFlowAndSetLiveData(flow)
    }

    fun getNearestPlaces() {
        val flow = placesRepository.getAllPlaces()
        filterPlacesFlowAndSetLiveData(flow)
    }

    private fun filterPlacesFlowAndSetLiveData(flowList: Flow<List<Place>>) = viewModelScope.launch {
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

    private fun calculateDistanceBetweenTwoLocationsInKm(from: LatLng, to: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(from, to) / 1000
    }
}