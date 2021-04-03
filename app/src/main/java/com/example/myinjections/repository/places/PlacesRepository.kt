package com.example.myinjections.repository.places

import com.example.myinjections.network.model.Place

interface PlacesRepository {
    suspend fun getAllPlaces(): List<Place>
}