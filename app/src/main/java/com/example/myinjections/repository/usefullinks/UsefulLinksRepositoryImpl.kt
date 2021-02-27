package com.example.myinjections.repository.usefullinks

import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.network.service.UsefulLinksService
import kotlinx.coroutines.flow.Flow

class UsefulLinksRepositoryImpl(private val usefulLinksService: UsefulLinksService): UsefulLinksRepository {
    override suspend fun getAllLinks(): Flow<List<UsefulLink>> {
        return usefulLinksService.getLinks()
    }
}