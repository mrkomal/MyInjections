package com.example.myinjections.repository.usefullinks

import com.example.myinjections.network.model.UsefulLink
import kotlinx.coroutines.flow.Flow

interface UsefulLinksRepository {
    suspend fun getAllLinks(): Flow<List<UsefulLink>>
}