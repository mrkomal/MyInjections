package com.example.myinjections.repository

import android.app.Application
import com.example.myinjections.room.database.InjectionsDatabase
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.room.model.InjectionsDao
import kotlinx.coroutines.flow.Flow

class InjectionsRepositoryImpl(application: Application): InjectionsRepository {

    private var injectionsDAO: InjectionsDao

    init {
        val injectionDatabase = InjectionsDatabase.getInstance(application)
        injectionsDAO = injectionDatabase.injectionsDao()
    }

    override fun getAllInjectionInfo() : Flow<List<InjectionInfo>> = injectionsDAO.getAllInfo()

    override suspend fun insertInjectionInfo(injectionInfo: InjectionInfo){
        injectionsDAO.insert(injectionInfo)
    }

}