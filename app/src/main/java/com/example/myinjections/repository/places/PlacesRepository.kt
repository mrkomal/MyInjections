package com.example.myinjections.repository.places

import com.example.myinjections.network.model.Place
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {
    fun getAllPlaces(): Flow<List<Place>>

    fun getAllPharmacies(): Flow<List<Place>>

    fun getAllClinics(): Flow<List<Place>>
}