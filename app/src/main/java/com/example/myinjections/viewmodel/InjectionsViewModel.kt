package com.example.myinjections.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.launch

class InjectionsViewModel {

    class InjectionsViewModel(private val repository: InjectionsRepository) : ViewModel() {

        var injectionsInfo: LiveData<List<InjectionInfo>> = repository.getAllInjectionInfo().asLiveData()

        fun insertInjectionInfo(injectionInfo: InjectionInfo) = viewModelScope.launch {
            repository.insertInjectionInfo(injectionInfo)
        }

    }

}