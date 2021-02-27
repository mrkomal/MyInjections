package com.example.myinjections.repository.usefullinks

import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.network.service.UsefulLinksService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsefulLinksRepositoryImpl(private val usefulLinksService: UsefulLinksService) : UsefulLinksRepository {

    override fun getAllLinks(): Flow<List<UsefulLink>> = flow {
        emit(usefulLinksService.getLinks())
    }
}