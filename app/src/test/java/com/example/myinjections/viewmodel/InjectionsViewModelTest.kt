package com.example.myinjections.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.core.app.ApplicationProvider
import com.example.myinjections.InjectionsApplication
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.repository.FakeInjectionsRepositoryImpl
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.room.model.InjectionInfo
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.compat.ViewModelCompat.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.mockito.Mockito.mock

class InjectionsViewModelTest : KoinTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

//    private val injectionsViewModelTestModule = module {
//        single { FakeInjectionsRepositoryImpl() }
//        viewModel { InjectionsViewModel(get()) }
//    }

   // private val fakeInjectionsViewModel: InjectionsViewModel = get()
   //private val fakeInjectionsViewModel: InjectionsViewModel by inject()

    private lateinit var repository: InjectionsRepository
    private lateinit var viewModel: InjectionsViewModel

    @Before
    fun setup() {
//        startKoin{
//            androidContext(mock(Context::class.java))
//            modules(injectionsViewModelTestModule)
//        }
       // val viewModel: InjectionsViewModel = getViewModel()
        repository = FakeInjectionsRepositoryImpl()
        viewModel = InjectionsViewModel(repository)
    }

    @After
    fun tearDown() {
//        stopKoin()
    }

    @Test
    fun getAllInjectionInfoWorksCorrectly_returns_true() {
        //val fakeInjectionsViewModel: InjectionsViewModel = get()
//        val repository: InjectionsRepository by inject()
        //val fakeInjectionsViewModel: InjectionsViewModel by inject()
        //val a = repository.getAllInjectionInfo()
        //assertThat(a == listOf<InjectionInfo>())
//        val viewModel = InjectionsViewModel(repository)
        val allInfo = viewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(3, allInfo.count())
    }
}