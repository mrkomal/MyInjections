package com.example.myinjections.repository.usefullinks

import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.network.service.UsefulLinksService
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
import org.mockito.Mockito.mock

class UsefulLinksRepositoryTest : KoinTest {

    //Test subject
    private val testRepository: UsefulLinksRepository by inject()

    //Collaborators
    private val mockService: UsefulLinksService by inject()

    //Utilities
    private val mockUsefulLink: UsefulLink by lazy {
        UsefulLink(1,"TitleFromApi", "SubjectFromApi","LinkFromService")
    }

    @Before
    fun setUp() {
        startKoin {
            modules(
                module{
                    single<UsefulLinksRepository> { UsefulLinksRepositoryImpl(get()) }
                    single<UsefulLinksService> { mock(UsefulLinksService::class.java) }
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
    fun getAllLinksWorksCorrectly_returnTrue() = runBlocking {
        Mockito.`when`(mockService.getLinks()).thenReturn(listOf(mockUsefulLink))
        assert(testRepository.getAllLinks().firstOrNull()?.contains(mockUsefulLink) ?: false)
    }
}