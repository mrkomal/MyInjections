package com.example.myinjections.network.service

import com.example.myinjections.modules.networkModule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class PlaceServiceTest : KoinTest {

    private val service: PlaceService by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(networkModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAllPlacesWorksCorrectly_returnsTrue() = runBlocking{
        assertTrue(service.getAllPlaces().size == 3)
    }

    @Test
    fun getOnlyPharmaciesWorksCorrectly_returnsTrue() = runBlocking {
        assertTrue(service.getAllPlacesOfSpecifiedType(place = "pharmacy").size == 3)
    }
}