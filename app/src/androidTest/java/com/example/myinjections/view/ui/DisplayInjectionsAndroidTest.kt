package com.example.myinjections.view.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myinjections.R
import com.example.myinjections.getOrAwaitValue
import com.example.myinjections.modules.viewModelTestModule
import com.example.myinjections.view.adapters.InjectionsListAdapter
import com.example.myinjections.viewmodel.InjectionsViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.containsString
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class DisplayInjectionsAndroidTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var scenario: ActivityScenario<DisplayInjectionActivity>
    private val testInjectionsViewModel: InjectionsViewModel by inject()

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            modules(viewModelTestModule)
        }

        // launch activity scenario with injected fake repository and replaced ViewModel
        scenario = ActivityScenario.launch(DisplayInjectionActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
        stopKoin()
    }

    @Test
    fun injectionsRecyclerViewIsVisible_returnsTrue() {
        onView(withId(R.id.injections_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun swipeToDeleteWorksCorrectly_returnsTrue(): Unit = runBlocking {
        // get list size before swipe
        val sizeBeforeSwipe = testInjectionsViewModel.resultInjectionInformation
            .getOrAwaitValue()
            .size

        // swipe first item to left
        onView(withId(R.id.injections_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<InjectionsListAdapter.InjectionsListViewHolder>(
                0,
                swipeLeft()
            )
        )

        // get list size after swipe
        val sizeAfterSwipe = testInjectionsViewModel.resultInjectionInformation
            .getOrAwaitValue()
            .size

        assertTrue("Item was not deleted.", sizeAfterSwipe == (sizeBeforeSwipe-1))
    }

    @Test
    fun snackBarIsDisplayedAfterSwiping_returnsTrue() {
        // swipe first item to left
        onView(withId(R.id.injections_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<InjectionsListAdapter.InjectionsListViewHolder>(
                0,
                swipeLeft()
            )
        )

        val context = ApplicationProvider.getApplicationContext<Context>()
        onView(withText(containsString(context.getString(R.string.snackbar_info))))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}