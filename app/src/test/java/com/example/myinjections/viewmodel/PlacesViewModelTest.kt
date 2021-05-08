package com.example.myinjections.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myinjections.InjectionsApplication
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.network.model.Place
import com.example.myinjections.repository.places.PlacesRepository
import com.example.myinjections.tools.InternetConnectionState
import com.google.android.gms.maps.model.LatLng
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidApplication
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
    private val app: Application by inject()
    private val conn: InternetConnectionState by inject()
    private val mockRepository: PlacesRepository by inject()

    // Fake service answer
    private val mockServiceAnswer = listOf(
        Place(1, "pharmacy", "a", 60.0,60.0),
        Place(2, "clinic", "b", 60.0,60.0),
        Place(3, "pharmacy", "c", 50.06,19.93),
        Place(4, "clinic", "d", 50.063591321304735,19.942850917953475)
    )

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single { PlacesViewModel(get(), get(), get()) }
                    single<PlacesRepository> { mock(PlacesRepository::class.java) }
                    single<Application> { mock(Application::class.java) }
                    single<InternetConnectionState> { mock(InternetConnectionState::class.java) }
                }
            )
        }

        Mockito.`when`(mockRepository.getAllPlaces()).thenReturn(
            flow {
                emit(mockServiceAnswer)
            }
        )

        Mockito.`when`(conn.hasInternetConnection(app)).thenReturn(true)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getNearbyPlaces_worksCorrectly() {
        testViewModel.getNearestPlaces()
        val correctNumberOfPlaces = 2
        val actualNumberOfPlaces = testViewModel.places.getOrAwaitValueTest().size

        assertTrue("Wrong number of places. Should be $correctNumberOfPlaces, but get $actualNumberOfPlaces.",
            actualNumberOfPlaces == correctNumberOfPlaces)
    }

    @Test
    fun calculateDistanceBetweenTwoLocationsInKm_worksCorrectly() {
        val latLng1 = LatLng(0.0,0.0)
        val latLng2 = LatLng(1.0,1.0)

        val correctDistance = 157.2496034104515
        val calculatedDistance = testViewModel.calculateDistanceBetweenTwoLocationsInKm(latLng1,latLng2)

        assertTrue("Expected distance: $correctDistance, but got $calculatedDistance",
            calculatedDistance == correctDistance)
    }

    @Test
    fun getInternetConnection_returnsTrue() {
        assertTrue("Invalid value.", testViewModel.getInternetConnection())
    }
}