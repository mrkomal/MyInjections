package com.example.myinjections.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.network.model.Place
import com.example.myinjections.repository.places.PlacesRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.Mockito.mock


class PlacesViewModelTest: KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testViewModel: PlacesViewModel by inject()

    // Mock
    private val mockRepository: PlacesRepository by inject()
    private val mockServiceAnswer = listOf(
        Place(1, "pharmacy", "a", 50.0,50.0) ,
        Place(2, "not-pharmacy", "b", 60.0,60.0)
    )

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single { PlacesViewModel( get() ) }
                    single<PlacesRepository> { mock(PlacesRepository::class.java) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

//    @Test
//    fun getAllPlaces_worksCorrectly() {
//        Mockito.`when`(mockRepository.getAllPlaces()).thenReturn(
//            flow {
//                emit(mockServiceAnswer)
//            }
//        )
//
//        assert(testViewModel.places.getOrAwaitValueTest().size == 2)
//    }

    @Test
    fun getNearbyPlaces_worksCorrectly() {
        Mockito.`when`(mockRepository.getAllPlaces()).thenReturn(
            flow {
                emit(mockServiceAnswer)
            }
        )

        testViewModel.getNearestPlaces()
        assertTrue("Too many places.",testViewModel.places.getOrAwaitValueTest().isEmpty())
    }
}