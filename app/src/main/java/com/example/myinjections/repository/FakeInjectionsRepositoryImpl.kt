package com.example.myinjections.repository

import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeInjectionsRepositoryImpl : InjectionsRepository{

    private var _fakeInjectionsInfoList = mutableListOf(
        InjectionInfo(0,"aaa",2020, 0.1, true, "xxx"),
        InjectionInfo(0,"bbb",2019, 0.3, false, "yyy"),
        InjectionInfo(0,"ccc",2018, 0.2, true, "zzz")
    )
    private val fakeInjectionsInfoList: List<InjectionInfo>
        get() = _fakeInjectionsInfoList


    override fun getAllInjectionInfo(): Flow<List<InjectionInfo>> = flow {
        emit(fakeInjectionsInfoList)
    }

    override suspend fun insertInjectionInfo(injectionInfo: InjectionInfo) {
        _fakeInjectionsInfoList.add(injectionInfo)
    }

    override suspend fun deleteInjectionInfo(injectionInfo: InjectionInfo) {
        _fakeInjectionsInfoList.remove(injectionInfo)
    }

}