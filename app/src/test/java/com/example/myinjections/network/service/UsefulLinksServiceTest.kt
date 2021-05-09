package com.example.myinjections.network.service

import com.example.myinjections.modules.networkModule
import com.example.myinjections.network.model.UsefulLink
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
    private val usefulLink = UsefulLink(
        id=1,
        subject= "Worth to know",
        title = "What is vaccination?",
        link = "https://en.wikipedia.org/wiki/Vaccination",
        image_url = "https://cdnuploads.aa.com.tr/uploads/Contents/2020/12/17/thumbs_b_c_8aa06369577e63dca032e21f3105e919.jpg?v=125923",
        sample_text = "Vaccination is the administration of a vaccine to help the immune system develop protection from a disease. Vaccines contain a microorganism or virus in a weakened, live or killed state, or proteins or toxins..."
    )

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
    fun apiGetLinksFunctionReturnsExpectedNumberOfUsefulLinks_returnsTrue(): Unit = runBlocking {
        val numOfObjectsFromApi = service.getLinks().count()
        val correctNumber = 6

        assertTrue("Api returns wrong number of elements.", numOfObjectsFromApi == correctNumber)
    }

    @Test
    fun apiGetLinksFunctionReturnsCorrectObjects_returnsTrue(): Unit = runBlocking {
        assertTrue("Api does not return expected element.", service.getLinks().contains(usefulLink))
    }
}