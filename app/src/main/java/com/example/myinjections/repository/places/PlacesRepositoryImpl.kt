package com.example.myinjections.repository.places

import com.example.myinjections.network.model.Place
import com.example.myinjections.network.service.PlaceService

class PlacesRepositoryImpl(private val placeService: PlaceService): PlacesRepository {
    override suspend fun getAllPlaces(): List<Place> {
        return placeService.getAllPlaces()
    }

}