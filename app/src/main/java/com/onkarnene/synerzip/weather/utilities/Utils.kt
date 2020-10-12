package com.onkarnene.synerzip.weather.utilities

import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.onkarnene.synerzip.weather.App
import com.onkarnene.synerzip.weather.utilities.Unit.IMPERIAL
import com.onkarnene.synerzip.weather.utilities.Unit.METRIC
import com.onkarnene.synerzip.weather.utilities.Unit.STANDARD
import kotlin.math.roundToInt

object Utils {
	
	/**
	 * @return respective string resource.
	 */
	fun getString(@StringRes resId: Int): String = App.getContext().getString(resId)
	
	/**
	 * @return respective color resource.
	 */
	fun getColor(@ColorRes resId: Int) = ContextCompat.getColor(App.getContext(), resId)
	
	/**
	 * @return respective string-array resource.
	 */
	fun getStringArray(@ArrayRes resId: Int): Array<String> = App.getContext().resources.getStringArray(resId)
	
	/**
	 * @return respective int-array resource.
	 */
	fun getIntArray(@ArrayRes resId: Int): IntArray = App.getContext().resources.getIntArray(resId)
	
	/**
	 * @return respective color resource.
	 */
	fun getDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(App.getContext(), resId)
	
	fun calculateVisibility(visibility: Int): String = if (visibility == DEFAULT_INT) {
		NA
	} else {
		"${visibility / 1000} km"
	}
	
	fun getIconUrl(iconCode: String?) = "http://openweathermap.org/img/wn/$iconCode@2x.png"
	
	fun roundOffTemperature(temp: Double): String = when (PreferencesUtils.getUnit()) {
		STANDARD -> "${temp.roundToInt()}$DEGREE_KELVIN"
		METRIC -> "${temp.roundToInt()}$DEGREE_CELSIUS"
		IMPERIAL -> "${temp.roundToInt()}$DEGREE_FAHRENHEIT"
	}
	
	fun formatWindSpeed(speed: Double): String = when (PreferencesUtils.getUnit()) {
		STANDARD -> "$speed $METER_PER_SECOND"
		METRIC -> "$speed $METER_PER_SECOND"
		IMPERIAL -> "$speed $MILES_PER_HOUR"
	}
}