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
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest

class InjectionsViewModelTest : KoinTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var testRepository: InjectionsRepository
    private lateinit var testViewModel: InjectionsViewModel

    @Before
    fun setup() {
        testRepository = FakeInjectionsRepositoryImpl()
        testViewModel = InjectionsViewModel(testRepository)
    }

    @After
    fun tearDown() { }

    @Test
    fun getAllInjectionInfoWorksCorrectly_returns_true() {
        val allInfo = testViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(3, allInfo.count())
    }
}