package com.onkarnene.synerzip.weather

import android.graphics.Point
import android.graphics.Rect
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.google.android.material.internal.CheckableImageButton
import org.hamcrest.*
import org.hamcrest.Matchers.*
import kotlin.annotation.AnnotationRetention.SOURCE

class ClickDrawableAction(@Location private val drawableLocation: Int) : ViewAction {
	
	companion object {
		
		const val Left = 0
		const val Top = 1
		const val Right = 2
		const val Bottom = 3
	}
	
	override fun getConstraints(): Matcher<View> {
		return allOf(isAssignableFrom(TextView::class.java), object : BoundedMatcher<View?, TextView>(TextView::class.java) {
			override fun matchesSafely(tv: TextView): Boolean {
				//get focus so drawables are visible and if the TextView has a drawable in the position then return a match
				return tv.requestFocusFromTouch() && tv.compoundDrawables[drawableLocation] != null
			}
			
			override fun describeTo(description: Description) {
				description.appendText("has drawable")
			}
		})
	}
	
	override fun getDescription(): String = "click drawable"
	
	override fun perform(
			uiController: UiController?,
			view: View?
	) {
		val tv = view as CheckableImageButton? //we matched
		
		if (tv != null && tv.requestFocusFromTouch()) //get focus so drawables are visible
		{
			//get the bounds of the drawable image
			val drawableBounds: Rect = tv.clipBounds ?: Rect()
			
			//calculate the drawable click location for left, top, right, bottom
			val clickPoint: Array<Point?> = arrayOfNulls(4)
			clickPoint[Left] = Point(tv.left + drawableBounds.width() / 2, (tv.pivotY + drawableBounds.height() / 2).toInt())
			clickPoint[Top] = Point((tv.pivotX + drawableBounds.width() / 2).toInt(), tv.top + drawableBounds.height() / 2)
			clickPoint[Right] = Point(tv.right + drawableBounds.width() / 2, (tv.pivotY + drawableBounds.height() / 2).toInt())
			clickPoint[Bottom] = Point((tv.pivotX + drawableBounds.width() / 2).toInt(), tv.bottom + drawableBounds.height() / 2)
			
			val eventDown = MotionEvent.obtain(SystemClock.uptimeMillis(),
			                                   SystemClock.uptimeMillis(),
			                                   MotionEvent.ACTION_DOWN,
			                                   clickPoint[drawableLocation]?.x?.toFloat() ?: 0F,
			                                   clickPoint[drawableLocation]?.y?.toFloat() ?: 0F,
			                                   0)
			val eventUp = MotionEvent.obtain(SystemClock.uptimeMillis(),
			                                 SystemClock.uptimeMillis(),
			                                 MotionEvent.ACTION_UP,
			                                 clickPoint[drawableLocation]?.x?.toFloat() ?: 0F,
			                                 clickPoint[drawableLocation]?.y?.toFloat() ?: 0F,
			                                 0)
			if (tv.dispatchTouchEvent(eventDown)) tv.dispatchTouchEvent(eventUp)
		}
	}
	
	@IntDef(Left, Top, Right, Bottom)
	@Retention(SOURCE)
	annotation class Location
}