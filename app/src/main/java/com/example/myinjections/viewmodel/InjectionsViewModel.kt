package com.example.myinjections.viewmodel

import androidx.lifecycle.*
import com.example.myinjections.repository.injections.InjectionsRepository
import com.example.myinjections.room.model.InjectionInfo
import kotlinx.coroutines.launch


enum class SortType {
    //enum for declaring possible filtering options for injections data that will be displayed on the screen
    DEFAULT, BY_NAME, BY_YEAR, BY_DOSE, ONLY_OBLIGATORY, ONLY_OPTIONAL,
    FILTER_BY_NAME
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
                SortType.BY_DOSE -> injectionInfoSortedByDose
                SortType.ONLY_OBLIGATORY -> injectionInfoObligatory
                SortType.ONLY_OPTIONAL -> injectionInfoOptional
                SortType.FILTER_BY_NAME -> filteredInjectionInfo
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

    private val injectionInfoSortedByDose by lazy {
        injectionsInfo.map { list ->
            list.sortedWith(
                compareBy { it.dose }
            )
        }
    }

    private val injectionInfoObligatory by lazy {
        injectionsInfo.map { list ->
            list.filter {
                it.isObligatory
            }
        }
    }

    private val injectionInfoOptional by lazy {
        injectionsInfo.map { list ->
            list.filter {
                !it.isObligatory
            }
        }
    }

    // Functions for setting sort type (data order is changed after triggering them).
    fun sortInjectionsInfoByName() { chosenFilterType.value = SortType.BY_NAME }

    fun sortInjectionsInfoByYear() { chosenFilterType.value = SortType.BY_YEAR }

    fun sortInjectionInfoByDose() { chosenFilterType.value = SortType.BY_DOSE }

    fun showObligatoryInjectionInfo() { chosenFilterType.value = SortType.ONLY_OBLIGATORY }

    fun showOptionalInjectionInfo() { chosenFilterType.value = SortType.ONLY_OPTIONAL }

    // Filtering by name or illness information for SearchView
    private var filteredInjectionInfo = injectionsInfo

    fun filterInjectionInfo(containedString: String) {
        filteredInjectionInfo = injectionsInfo.map { list ->
            list.filter {
                it.name.contains(containedString) or it.illnessInformation.contains(containedString)
            }
        }

        chosenFilterType.value = SortType.FILTER_BY_NAME
    }

    // Other
    fun insertInjectionInfo(injectionInfo: InjectionInfo) = viewModelScope.launch {
        repository.insertInjectionInfo(injectionInfo)
    }

    fun deleteInjectionInfo(injectionInfo: InjectionInfo) = viewModelScope.launch {
        repository.deleteInjectionInfo(injectionInfo)
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
