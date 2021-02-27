package com.example.myinjections.viewmodel

import androidx.lifecycle.*
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.repository.usefullinks.UsefulLinksRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class UsefulLinksViewModel(private val repository: UsefulLinksRepository): ViewModel() {

    private val _allLinks = repository.getAllLinks().asLiveData()
    val allLinks: LiveData<List<UsefulLink>>
        get() = _allLinks
}