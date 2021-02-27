package com.example.myinjections.repository.injections

import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.flow.Flow

interface InjectionsRepository {

    fun getAllInjectionInfo() : Flow<List<InjectionInfo>>

    suspend fun insertInjectionInfo(injectionInfo: InjectionInfo)

    suspend fun deleteInjectionInfo(injectionInfo: InjectionInfo)

}