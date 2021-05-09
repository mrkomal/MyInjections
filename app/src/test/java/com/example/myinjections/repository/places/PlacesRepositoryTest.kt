package com.example.myinjections.repository.places

import com.example.myinjections.network.model.Place
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.network.service.PlaceService
import com.example.myinjections.network.service.UsefulLinksService
import com.example.myinjections.repository.usefullinks.UsefulLinksRepository
import com.example.myinjections.repository.usefullinks.UsefulLinksRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito

class PlacesRepositoryTest: KoinTest {

    //Test subject
    private val testRepository: PlacesRepository by inject()

    //Collaborators
    private val mockService: PlaceService by inject()

    //Utilities
    private val mockPlacesList: List<Place> = listOf(
        Place(0,"pharmacy", "A", 0.0, 0.0),
        Place(1,"n", "B", 0.0, 0.0)
    )

    @Before
    fun setUp() {
        startKoin {
            modules(
                module{
                    single<PlacesRepository> { PlacesRepositoryImpl(get()) }
                    single<PlaceService> { Mockito.mock(PlaceService::class.java) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @InternalCoroutinesApi
    @Test
    fun getAllPlacesWorksCorrectly_returnTrue() = runBlocking {
        Mockito.`when`(mockService.getAllPlaces()).thenReturn(mockPlacesList)
        assert(testRepository.getAllPlaces().firstOrNull()?.contains(mockPlacesList[0]) ?: false)
    }

}