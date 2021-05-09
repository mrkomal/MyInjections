package com.example.myinjections.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.repository.usefullinks.UsefulLinksRepository
import com.example.myinjections.repository.usefullinks.UsefulLinksRepositoryImpl
import com.example.myinjections.tools.InternetConnectionState
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.Mockito.mock

class UsefulLinksViewModelTest : KoinTest {

    //Rules
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    //Object to test
    private val testViewModel: UsefulLinksViewModel by inject()

    //Mock objects
    private val mockRepository: UsefulLinksRepository by inject()
    private val mockApp: Application by inject()
    private val mockConn: InternetConnectionState by inject()

    //Utilities
    private val mockUsefulLink: UsefulLink = UsefulLink(1,"TitleFromApi", "SubjectFromApi",
        "LinkFromService", image_url = "A", sample_text = "B")

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    viewModel { UsefulLinksViewModel(get(), get(), get()) }
                    single<UsefulLinksRepository> { mock(UsefulLinksRepository::class.java) }
                    single<Application> { mock(Application::class.java) }
                    single<InternetConnectionState> { mock(InternetConnectionState::class.java) }
                }
            )
        }

        Mockito.`when`(mockRepository.getAllLinks()).thenReturn(flow{
            emit(listOf(mockUsefulLink))
        })

        Mockito.`when`(mockConn.hasInternetConnection(mockApp)).thenReturn(true)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAllLinksWorksCorrectly_returnTrue() {
        testViewModel.getAllLinks()
        val valsFromViewModel = testViewModel.allLinks.getOrAwaitValueTest()
        assertTrue("LiveData does not contain required value.",
            valsFromViewModel.contains(mockUsefulLink))
    }
}