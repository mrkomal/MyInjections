package com.example.myinjections.repository.places

import com.example.myinjections.network.model.Place
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {
    suspend fun getAllPlaces(): Flow<List<Place>>

    suspend fun getAllPharmacies(): Flow<List<Place>>
}