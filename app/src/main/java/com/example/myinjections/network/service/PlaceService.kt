package com.example.myinjections.network.service

import com.example.myinjections.network.model.Place
import retrofit2.http.GET

interface PlaceService {
    @GET("places")
    suspend fun getAllPlaces(): List<Place>
}