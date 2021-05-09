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
        val correctNumber = 14
        val obtainedNumber = service.getAllPlaces().size
        assertTrue("Wrong number of places. Got $obtainedNumber, instead of $correctNumber",
            correctNumber == obtainedNumber)
    }

    @Test
    fun getOnlyPharmaciesWorksCorrectly_returnsTrue() = runBlocking {
        val correctNumber = 8
        val obtainedNumber = service.getAllPlacesOfSpecifiedType(place = "pharmacy").size
        assertTrue("Wrong number of places of pharmacy type. Got $obtainedNumber, instead of $correctNumber.",
            correctNumber == obtainedNumber)
    }
}