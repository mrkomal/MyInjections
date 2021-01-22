package com.example.myinjections.repository

import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.room.model.InjectionsDao
import kotlinx.coroutines.flow.Flow

class InjectionsRepositoryImpl(private var injectionsDAO: InjectionsDao): InjectionsRepository {

    override fun getAllInjectionInfo() : Flow<List<InjectionInfo>> = injectionsDAO.getAllInfo()

    override suspend fun insertInjectionInfo(injectionInfo: InjectionInfo){
        injectionsDAO.insert(injectionInfo)
    }

}