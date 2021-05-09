package com.example.myinjections.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myinjections.MainCoroutineRule
import com.example.myinjections.getOrAwaitValueTest
import com.example.myinjections.modules.viewModelTestModule
import com.example.myinjections.room.model.InjectionInfo
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class InjectionsViewModelTest : KoinTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var testInjectionsViewModel: InjectionsViewModel

    @Before
    fun setup() {
        startKoin {
            modules(viewModelTestModule)
        }

        testInjectionsViewModel = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAllInjectionInfoWorksCorrectly_returnsTrue() {
        val allInfo = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(3, allInfo.count())
    }

    @Test
    fun addNewInjectionInfoWorksCorrectly_returnsTrue() {
        val newInjectionInfo = InjectionInfo(
            0,"ddd",2018, 0.6, false, "xxx"
        )
        testInjectionsViewModel.insertInjectionInfo(newInjectionInfo)

        val allInfo = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(4, allInfo.count())
    }

    @Test
    fun deleteInjectionInfoWorksCorrectly_returnsTrue() {
        val newInjectionInfo = InjectionInfo(
            0,"eee",2018, 0.6, false, "xxx"
        )
        testInjectionsViewModel.insertInjectionInfo(newInjectionInfo)
        val infoAfterInsert = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertTrue("Data was not added.",infoAfterInsert.count() == 4)

        testInjectionsViewModel.deleteInjectionInfo(newInjectionInfo)
        val infoAfterDelete = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertTrue("Data was added, but not removed.", infoAfterDelete == infoAfterInsert-1)
    }

    @Test
    fun sortInjectionInfoByNameWorksCorrectly_returnsTrue() {
        val desiredListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest().sortedBy {
            it.name
        }

        testInjectionsViewModel.sortInjectionsInfoByName()
        val afterSortFunctionListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(desiredListOrder, afterSortFunctionListOrder)
    }

    @Test
    fun sortInjectionInfoByYearWorksCorrectly_returnsTrue() {
        val desiredListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest().sortedBy {
            it.date
        }

        testInjectionsViewModel.sortInjectionsInfoByYear()
        val afterSortFunctionListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(desiredListOrder, afterSortFunctionListOrder)
    }

    @Test
    fun sortInjectionInfoByDoseWorksCorrectly_returnsTrue() {
        val desiredListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest().sortedBy {
            it.dose
        }

        testInjectionsViewModel.sortInjectionInfoByDose()
        val afterSortFunctionListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(desiredListOrder, afterSortFunctionListOrder)
    }

    @Test
    fun showOnlyObligatoryInjectionsWorksCorrectly_returnsTrue() {
        val desiredList = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest().filter {
            it.isObligatory
        }

        testInjectionsViewModel.showObligatoryInjectionInfo()
        val afterSortFunctionListOrder = testInjectionsViewModel.resultInjectionInformation.getOrAwaitValueTest()
        assertEquals(desiredList, afterSortFunctionListOrder)
    }

    @Test
    fun filterInjectionInfoByGivenSubStringWorksCorrectly_returnsTrue(){
        val subString = "a"
        val desiredInfoSize = testInjectionsViewModel.resultInjectionInformation
            .getOrAwaitValueTest()
            .filter { it.name.contains(subString) or it.illnessInformation.contains(subString)}
            .size

        testInjectionsViewModel.filterInjectionInfo(subString)
        val infoAfterFiltering = testInjectionsViewModel.resultInjectionInformation
            .getOrAwaitValueTest()
            .size
        assertTrue(desiredInfoSize == infoAfterFiltering)
    }
}