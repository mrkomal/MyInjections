package com.example.myinjections.room.model

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.myinjections.room.database.InjectionsDatabase
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@SmallTest
class InjectionsDaoTest : KoinTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val roomTestModule = module {
        single { ApplicationProvider.getApplicationContext<Context>() as Context}
        single { Room.inMemoryDatabaseBuilder(get(), InjectionsDatabase::class.java).build() }
        single { get<InjectionsDatabase>().injectionsDao() }
    }

    private val injectionTestDao: InjectionsDao by inject()
    private val injectionsTestDB: InjectionsDatabase by inject()

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            modules(roomTestModule)
        }
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        injectionsTestDB.close()
        stopKoin()
    }

    @Test
    @Throws(Exception::class)
    fun insertNewInjectionToDatabase(): Unit = runBlocking {
        val startSize = injectionTestDao.getAllInfo().firstOrNull()?.size ?: 0

        val id = 0
        val name = "xyz_add"
        val date = 2020
        val dose = 0.1
        val oblig = true
        val info = "abc"
        val testInjectionInfo = InjectionInfo(id, name, date, dose, oblig, info)
        injectionTestDao.insert(testInjectionInfo)

        val injectionsFromDatabase = injectionTestDao.getAllInfo().firstOrNull() ?: listOf()
        val endSize = injectionsFromDatabase.size

        // Pass the test if testInfo is inside list returned from DB and if number of items in the list
        // increments by 1.
        assertTrue("Item was not added to database or is in valid format.",
            injectionsFromDatabase.any {
                it.name == name
                it.date == date
                it.dose == dose
                it.isObligatory == oblig
                it.illnessInformation == info }
        )
        assertTrue("Incorrect number of records.", (startSize+1) == endSize )
    }


    @Test
    @Throws(Exception::class)
    fun deleteInjectionInfoFromDatabase(): Unit = runBlocking {
        val startSize = injectionTestDao.getAllInfo().firstOrNull()?.size ?: 0

        val name = "xyz_delete"
        val date = 2020
        val dose = 0.1
        val oblig = true
        val info = "abc"
        val testInjectionInfo = InjectionInfo(0, name, date, dose, oblig, info)
        injectionTestDao.insert(testInjectionInfo)
        val injectionsFromDatabase = injectionTestDao.getAllInfo().firstOrNull() ?: listOf()
        val afterInsertSize = injectionsFromDatabase.size
        assertTrue("Item was not added successfully.", afterInsertSize == startSize+1)

        //get id of item that was added lately and set it as testInjectionInfo's id in database
        val testInjectionInfoFromDb = testInjectionInfo.copy(
            id = injectionTestDao.getAllInfo().firstOrNull()?.last()?.id ?: 0
        )
        injectionTestDao.delete(testInjectionInfoFromDb)
        val afterDeleteSize = injectionTestDao.getAllInfo().firstOrNull()?.size ?: 0
        assertTrue("Item was added but not deleted.", startSize == afterDeleteSize)
    }
}