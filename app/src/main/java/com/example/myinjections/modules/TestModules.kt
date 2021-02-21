package com.example.myinjections.modules

import com.example.myinjections.repository.FakeInjectionsRepositoryImpl
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.viewmodel.InjectionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelTestModule = module {
    single<InjectionsRepository> { FakeInjectionsRepositoryImpl() }
    viewModel { InjectionsViewModel(get()) }
}