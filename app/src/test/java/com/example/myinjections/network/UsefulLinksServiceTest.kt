package com.example.myinjections.network

import com.example.myinjections.modules.networkModule
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.network.service.UsefulLinksService
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class UsefulLinksServiceTest : KoinTest {

    private val service: UsefulLinksService by inject()
    
    // Example JSON that is inside database.
    private val usefulLink = UsefulLink(id=1, subject= "Worth to know", title = "What is vaccination?",
        link = "https://en.wikipedia.org/wiki/Vaccination")

    @Before
    fun setUp() {
        /*
        Application uses testing api so there is no need to create new api for testing.
        That is why network module with Retrofit definition will be used.
         */
        startKoin {
            modules(networkModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun test(): Unit = runBlocking {
        val valsFromApi = service.getLinks()
        assertTrue("Api returns nothing or does not contain searched element.",
            valsFromApi.contains(usefulLink)
        )
    }

}