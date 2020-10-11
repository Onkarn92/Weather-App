package com.onkarnene.synerzip.weather.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.onkarnene.synerzip.weather.models.Weather

/**
 * Type converters used by Room database for weather list.
 */
class Converters {
	
	@TypeConverter
	fun toWeather(value: String): List<Weather> = arrayListOf<Weather>().apply {
		try {
			val weather = Gson().fromJson(value, Array<Weather>::class.java).toList()
			addAll(weather)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
	
	@TypeConverter
	fun fromWeather(value: List<Weather>): String = Gson().toJson(value)
}