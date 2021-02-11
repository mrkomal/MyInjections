package com.example.myinjections.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.repository.FakeInjectionsRepositoryImpl
import com.example.myinjections.repository.InjectionsRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class InjectionsViewModelTest : KoinTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val viewModelTestModule = module {
        single<InjectionsRepository> { FakeInjectionsRepositoryImpl() }
        viewModel { InjectionsViewModel(get()) }
    }

    private lateinit var testInjectionsViewModel: InjectionsViewModel

    @Before
    fun setup() {
        startKoin {
            modules(viewModelTestModule)
        }

        testInjectionsViewModel = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAllInjectionInfoWorksCorrectly_returnsTrue() {
        val allInfo = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(3, allInfo.count())
    }
}