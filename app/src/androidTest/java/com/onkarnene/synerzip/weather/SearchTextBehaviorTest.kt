package com.onkarnene.synerzip.weather

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.onkarnene.synerzip.weather.views.MainActivity
import org.hamcrest.*
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchTextBehaviorTest {
	
	private lateinit var searchQueryOne: String
	private lateinit var searchQueryTwo: String
	
	@get:Rule var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
	
	@Before
	fun init() {
		searchQueryOne = "Las Vegas"
		searchQueryTwo = "Mumbai, IN"
	}
	
	@Test
	fun searchCityName_mainActivity() {
		// Type sample search query.
		onView(withId(R.id.searchEdit)).perform(typeText(searchQueryOne), pressImeActionButton(), closeSoftKeyboard())
		onView(isRoot()).perform(waitFor(2000))
		
		// Compare entered search query with city name text.
		onView(withId(R.id.nameText)).check(matches(withSubstring(searchQueryOne)))
		onView(isRoot()).perform(waitFor(1500))
	}
	
	@Test
	fun clearText_searchCityName_mainActivity() {
		// Clear text and search another query.
		onView(withId(R.id.searchEdit)).perform(clearText(), closeSoftKeyboard())
		
		// Type sample search query.
		onView(withId(R.id.searchEdit)).perform(typeText(searchQueryTwo), pressImeActionButton(), closeSoftKeyboard())
		onView(isRoot()).perform(waitFor(2000))
		
		// Compare entered search query with city name text.
		onView(withId(R.id.nameText)).check(matches(withSubstring(searchQueryTwo)))
		onView(isRoot()).perform(waitFor(1500))
	}
	
	@Suppress("SameParameterValue")
	private fun waitFor(delay: Long): ViewAction {
		return object : ViewAction {
			override fun perform(
					uiController: UiController?,
					view: View?
			) {
				uiController?.loopMainThreadForAtLeast(delay)
			}
			
			override fun getConstraints(): Matcher<View> = isRoot()
			
			override fun getDescription(): String = "wait for $delay milliseconds"
		}
	}
}