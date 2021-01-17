package com.example.myinjections.viewmodel

import androidx.lifecycle.*
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.launch

class InjectionsViewModel(private val repository: InjectionsRepository) : ViewModel() {

    var injectionsInfo: LiveData<List<InjectionInfo>> = repository.getAllInjectionInfo().asLiveData()

    fun insertInjectionInfo(injectionInfo: InjectionInfo) = viewModelScope.launch {
        repository.insertInjectionInfo(injectionInfo)
    }

}

class InjectionsViewModelFactory(private val repository: InjectionsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InjectionsViewModel::class.java)) {
            return InjectionsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
