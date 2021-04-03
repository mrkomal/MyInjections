package com.example.myinjections.network.service

import com.example.myinjections.network.model.Place
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("places")
    suspend fun getAllPlaces(): List<Place>

    @GET("places")
    suspend fun getAllPlacesOfSpecifiedType(@Query("place") place: String): List<Place>
}