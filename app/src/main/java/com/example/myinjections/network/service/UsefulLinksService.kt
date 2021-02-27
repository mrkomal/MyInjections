package com.example.myinjections.network.service

import com.example.myinjections.network.model.UsefulLink
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface UsefulLinksService {
    @GET("/posts")
    suspend fun getLinks(): Flow<List<UsefulLink>>
}