package com.example.myinjections.repository

import android.app.Application
import com.example.myinjections.room.database.InjectionsDatabase
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.room.model.InjectionsDao
import kotlinx.coroutines.flow.Flow

class InjectionsRepository(application: Application) {

    private var injectionsDAO: InjectionsDao

    init {
        val injectionDatabase = InjectionsDatabase.getInstance(application)
        injectionsDAO = injectionDatabase.injectionsDao()
    }

    fun getAllInjectionInfo() : Flow<List<InjectionInfo>> = injectionsDAO.getAllInfo()

    suspend fun insertInjectionInfo(injectionInfo: InjectionInfo){
        injectionsDAO.insert(injectionInfo)
    }

}