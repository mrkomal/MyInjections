package com.example.myinjections.view.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import com.example.myinjections.repository.InjectionsRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.myinjections.R
import com.example.myinjections.getOrAwaitValue
import com.example.myinjections.modules.viewModelTestModule
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers

class AddInjectionActivityAndroidTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var scenario: ActivityScenario<AddInjectionActivity>
    private var scenarioHasBeenAlreadyClosed: Boolean = false

    private val testRepository: InjectionsRepository by inject()

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            modules(
                viewModelTestModule
            )
        }

        /*
       AddInjectionActivity has button that is responsible for finishing activity.
       Because of that there is no need to close scenario in some tests where button is used.
       Closing already closed scenario crushes application.
       Set value to true, whenever you expect insert button to work correctly.
        */
        scenarioHasBeenAlreadyClosed = false

        // launch activity scenario with injected fake repository and replaced ViewModel
        scenario = ActivityScenario.launch(AddInjectionActivity::class.java)
    }

    @After
    fun tearDown() {
        if(!scenarioHasBeenAlreadyClosed){
            scenario.close()
        }
        stopKoin()
    }

    @Test
    fun addNewInjectionRecordWorksCorrectly_returnTrue()  {
        val numOfInformationAtTheBeginning = testRepository.getAllInjectionInfo()
            .asLiveData()
            .getOrAwaitValue()
            .size

        // fill obligatory fields and insert new record
        onView(withId(R.id.name_textView)).perform(typeText("Name"))
        onView(withId(R.id.illness_textView)).perform(typeText("Some illness"))
        closeSoftKeyboard()
        onView(withId(R.id.insert_button)).perform(scrollTo(), click())
        scenarioHasBeenAlreadyClosed = true

        val numOfInformationAtTheEnd = testRepository.getAllInjectionInfo()
            .asLiveData()
            .getOrAwaitValue()
            .size

        assertTrue("Data was not inserted.",
            (numOfInformationAtTheBeginning + 1) == numOfInformationAtTheEnd)
    }

    @Test
    fun dialogIsDisplayedWhenNameFieldIsEmpty_returnsTrue() {
        onView(withId(R.id.illness_textView)).perform(typeText("Some illness"))
        closeSoftKeyboard()
        onView(withId(R.id.insert_button)).perform(scrollTo(), click())
        onView(withId(R.id.dialog_Error_textView)).check(matches(isDisplayed()))
    }

    @Test
    fun dialogIsDisplayedWhenIllnessInfoFieldIsEmpty_returnsTrue() {
        onView(withId(R.id.name_textView)).perform(typeText("Name"))
        closeSoftKeyboard()
        onView(withId(R.id.insert_button)).perform(scrollTo(), click())
        onView(withId(R.id.dialog_Error_textView)).check(matches(isDisplayed()))
    }

    @Test
    fun insertButtonFinishesActivity_returnsTrue() {
        onView(withId(R.id.name_textView)).perform(typeText("Name"))
        onView(withId(R.id.illness_textView)).perform(typeText("Some illness"))
        closeSoftKeyboard()
        onView(withId(R.id.insert_button)).perform(scrollTo(), click())
        scenarioHasBeenAlreadyClosed = true
        assert(scenario.state.isAtLeast(Lifecycle.State.DESTROYED))
    }

    @Test
    fun okButtonInAlertDialogWorksCorrectly_returnsTrue() {
        onView(withId(R.id.insert_button)).perform(scrollTo(), click())
        onView(withText("OK")).perform(click())
        onView(withId(R.id.insert_button)).check(matches(isDisplayed()))
    }

    @Test
    fun onSucceedSnackbarIsDisplayed_returnsTrue() {
        onView(withId(R.id.name_textView)).perform(typeText("Name"))
        onView(withId(R.id.illness_textView)).perform(typeText("Some illness"))
        closeSoftKeyboard()
        onView(withId(R.id.insert_button)).perform(scrollTo(), click())
        scenarioHasBeenAlreadyClosed = true

        onView(withText(R.string.snackbarText))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}