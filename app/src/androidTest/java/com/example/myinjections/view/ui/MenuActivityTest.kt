package com.example.myinjections.view.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myinjections.R
import com.example.myinjections.modules.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MenuActivityTest: KoinTest {

    @get:Rule
    val intentsTestRule: IntentsTestRule<MenuActivity> = IntentsTestRule(MenuActivity::class.java)

//    @Before
//    fun setUp() { }

//    @After
//    fun tearDown() { }

    @Test
    fun clickButton_launchesAddInjectionActivity_returnsTrue() {
        onView(withId(R.id.add_injection_button)).perform(click())
        intended(hasComponent(AddInjectionActivity::class.java.name))
    }

    @Test
    fun clickButton_launchesDisplayInjectionsActivity_returnsTrue() {
        onView(withId(R.id.display_injections_button)).perform(click())
        intended(hasComponent(DisplayInjectionActivity::class.java.name))
    }

    @Test
    fun clickButton_launchesDisplayLinksActivity_returnsTrue() {
        onView(withId(R.id.display_links_button)).perform(click())
        intended(hasComponent(DisplayLinksActivity::class.java.name))
    }

    @Test
    fun clickButton_launchesMapsActivity_returnsTrue() {
        onView(withId(R.id.display_map_button)).perform(click())
        intended(hasComponent(MapsActivity::class.java.name))
    }

    @Test
    fun clickButton_launchesInjectionAmountActivity_returnsTrue() {
        onView(withId(R.id.display_injection_amount)).perform(click())
        intended(hasComponent(InjectionAmountActivity::class.java.name))
    }
}