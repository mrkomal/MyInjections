package com.example.myinjections.repository

import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.flow.Flow

interface InjectionsRepository {

    fun getAllInjectionInfo() : Flow<List<InjectionInfo>>

    suspend fun insertInjectionInfo(injectionInfo: InjectionInfo)

}