package com.example.myinjections.viewmodel

import androidx.lifecycle.*
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.launch


enum class SortType {
    //enum for declaring possible filtering options for injections data that will be displayed on the screen
    DEFAULT, BY_NAME, BY_YEAR
}


class InjectionsViewModel(private val repository: InjectionsRepository) : ViewModel() {

    // Setting filter type to default while launching ViewModel.
    private var chosenFilterType = MutableLiveData<SortType>(SortType.DEFAULT)

    // Get information from repository the way it is stored in DB (default filter).
    // This value is required to be created while launching as it will be later used to perform sorting on it.
    private val injectionsInfo = repository.getAllInjectionInfo().asLiveData()

    // Result LiveData that has observer. LiveData changes depending on passed sort type.
    private val _resultInjectionInformation: LiveData<List<InjectionInfo>> =
        Transformations.switchMap(chosenFilterType){ choice ->
            when(choice) {
                SortType.DEFAULT -> injectionsInfo
                SortType.BY_NAME -> injectionInfoSortedByName
                SortType.BY_YEAR -> injectionInfoSortedByYear
            }
    }
    val resultInjectionInformation: LiveData<List<InjectionInfo>>
        get() = _resultInjectionInformation

    // Variables that are used for other types of sorting than DEFAULT.
    // Initialized only when they are required.
    private val injectionInfoSortedByName by lazy {
        injectionsInfo.map { list ->
            list.sortedWith(
                compareBy { it.name }
            )
        }
    }

    private val injectionInfoSortedByYear by lazy {
        injectionsInfo.map { list ->
            list.sortedWith(
                compareBy { it.date }
            )
        }
    }

    // Functions for setting sort type (data order is changed after triggering them).
    fun sortInjectionsInfoByName() { chosenFilterType.value = SortType.BY_NAME }

    fun sortInjectionsInfoByYear() { chosenFilterType.value = SortType.BY_YEAR }

    // Other
    fun insertInjectionInfo(injectionInfo: InjectionInfo) = viewModelScope.launch {
        repository.insertInjectionInfo(injectionInfo)
    }


}

//class InjectionsViewModelFactory(private val repository: InjectionsRepository): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(InjectionsViewModel::class.java)) {
//            return InjectionsViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
