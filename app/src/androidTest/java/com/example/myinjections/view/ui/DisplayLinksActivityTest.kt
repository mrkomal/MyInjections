package com.example.myinjections.view.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myinjections.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class DisplayLinksActivityTest : KoinTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<DisplayLinksActivity>
            = ActivityScenarioRule(DisplayLinksActivity::class.java)

    @Before
    fun setUp() { }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun viewPagerIsVisible_returnsTrue() {
        onView(withId(R.id.links_viewpager)).check(matches(isDisplayed()))
    }
}