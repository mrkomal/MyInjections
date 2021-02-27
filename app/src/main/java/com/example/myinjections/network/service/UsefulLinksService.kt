package com.example.myinjections.network.service

import com.example.myinjections.network.model.UsefulLink
import retrofit2.http.GET

interface UsefulLinksService {
    @GET("posts")
    suspend fun getLinks(): List<UsefulLink>
}