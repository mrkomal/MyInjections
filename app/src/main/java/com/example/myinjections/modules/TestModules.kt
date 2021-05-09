package com.example.myinjections.modules

import com.example.myinjections.repository.injections.FakeInjectionsRepositoryImpl
import com.example.myinjections.repository.injections.InjectionsRepository
import com.example.myinjections.viewmodel.InjectionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelTestModule = module {
    single<InjectionsRepository> { FakeInjectionsRepositoryImpl() }
    viewModel { InjectionsViewModel(get()) }
}