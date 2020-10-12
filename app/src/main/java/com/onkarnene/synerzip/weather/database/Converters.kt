package com.onkarnene.synerzip.weather.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.onkarnene.synerzip.weather.models.Clouds
import com.onkarnene.synerzip.weather.models.Coord
import com.onkarnene.synerzip.weather.models.Main
import com.onkarnene.synerzip.weather.models.Rain
import com.onkarnene.synerzip.weather.models.Snow
import com.onkarnene.synerzip.weather.models.Sys
import com.onkarnene.synerzip.weather.models.Weather
import com.onkarnene.synerzip.weather.models.Wind

/**
 * Type converters used by Room database for weather list.
 */
class Converters {
	
	private val gson: Gson = Gson()
	
	@TypeConverter
	fun toWeather(value: String): List<Weather> = arrayListOf<Weather>().apply {
		try {
			val weather = gson.fromJson(value, Array<Weather>::class.java).toList()
			addAll(weather)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
	
	@TypeConverter
	fun fromWeather(value: List<Weather>): String = gson.toJson(value)
	
	@TypeConverter
	fun toCoord(value: String): Coord = gson.fromJson(value, Coord::class.java)
	
	@TypeConverter
	fun fromCoord(coord: Coord): String = gson.toJson(coord)
	
	@TypeConverter
	fun toMain(value: String): Main = gson.fromJson(value, Main::class.java)
	
	@TypeConverter
	fun fromMain(main: Main): String = gson.toJson(main)
	
	@TypeConverter
	fun toWind(value: String): Wind = gson.fromJson(value, Wind::class.java)
	
	@TypeConverter
	fun fromWind(wind: Wind): String = gson.toJson(wind)
	
	@TypeConverter
	fun toRain(value: String): Rain = gson.fromJson(value, Rain::class.java)
	
	@TypeConverter
	fun fromRain(rain: Rain): String = gson.toJson(rain)
	
	@TypeConverter
	fun toSnow(value: String): Snow = gson.fromJson(value, Snow::class.java)
	
	@TypeConverter
	fun fromSnow(snow: Snow): String = gson.toJson(snow)
	
	@TypeConverter
	fun toClouds(value: String): Clouds = gson.fromJson(value, Clouds::class.java)
	
	@TypeConverter
	fun fromClouds(clouds: Clouds): String = gson.toJson(clouds)
	
	@TypeConverter
	fun toSys(value: String): Sys = gson.fromJson(value, Sys::class.java)
	
	@TypeConverter
	fun fromSys(sys: Sys): String = gson.toJson(sys)
}