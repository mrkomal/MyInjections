package com.example.myinjections.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.repository.usefullinks.UsefulLinksRepository
import com.example.myinjections.repository.usefullinks.UsefulLinksRepositoryImpl
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
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

    //Utilities
    private val mockUsefulLink: UsefulLink by lazy {
        UsefulLink(1,"TitleFromApi", "SubjectFromApi","LinkFromService", image_url = "A", sample_text = "B")
    }

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    viewModel { UsefulLinksViewModel(get()) }
                    single<UsefulLinksRepository> { mock(UsefulLinksRepository::class.java) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAllLinksWorksCorrectly_returnTrue() = runBlocking {
        Mockito.`when`(mockRepository.getAllLinks()).thenReturn(flow{
            emit(listOf(mockUsefulLink))
        })

        val valsFromViewModel = testViewModel.allLinks.getOrAwaitValueTest()
        assertTrue("LiveData does not contain required value.",
            valsFromViewModel.contains(mockUsefulLink))
    }
}