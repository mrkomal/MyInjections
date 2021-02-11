package com.example.myinjections.repository

import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class FakeInjectionsRepositoryImpl : InjectionsRepository{

    private var _fakeInjectionsInfoList = mutableListOf<InjectionInfo>(
        InjectionInfo(0,"aaa",2020, 0.1, true, "xxx"),
        InjectionInfo(0,"bbb",2019, 0.3, false, "yyy"),
        InjectionInfo(0,"ccc",2018, 0.2, true, "zzz")
    )
//    @ExperimentalCoroutinesApi
//    private var fakeInjectionsInfoList = ConflatedBroadcastChannel(mutableListOf(
//    InjectionInfo(0,"aaa",2020, 0.1, true, "xxx"),
//    InjectionInfo(0,"bbb",2019, 0.3, false, "yyy"),
//    InjectionInfo(0,"ccc",2018, 0.2, true, "zzz")
//    ))
    private val fakeInjectionsInfoList: List<InjectionInfo>
        get() = _fakeInjectionsInfoList

    private val fakeAdditionalInjectionInfo = InjectionInfo(
        0,"ddd",1990, 0.3, false, "www"
    )

    override fun getAllInjectionInfo(): Flow<List<InjectionInfo>> = flow {
        emit(fakeInjectionsInfoList)
    }

//    @ExperimentalCoroutinesApi
//    @FlowPreview
//    override fun getAllInjectionInfo(): Flow<List<InjectionInfo>> = fakeInjectionsInfoList.asFlow()

    override suspend fun insertInjectionInfo(injectionInfo: InjectionInfo) {
        TODO("Not yet implemented")
    }

//    override suspend fun insertInjectionInfo(injectionInfo: InjectionInfo) {
//        _fakeInjectionsInfoList.toMutableList().add(fakeAdditionalInjectionInfo)
//    }


}