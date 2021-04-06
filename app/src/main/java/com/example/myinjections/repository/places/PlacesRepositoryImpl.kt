package com.example.myinjections.repository.places

import com.example.myinjections.network.model.Place
import com.example.myinjections.network.service.PlaceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlacesRepositoryImpl(private val placeService: PlaceService): PlacesRepository {

    override fun getAllPlaces(): Flow<List<Place>> = flow {
        emit(placeService.getAllPlaces())
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllPharmacies(): Flow<List<Place>> = flow {
        val placeType = "pharmacy"
        emit(placeService.getAllPlacesOfSpecifiedType(placeType))
    }

}