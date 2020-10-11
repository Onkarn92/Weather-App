package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT
import com.onkarnene.synerzip.weather.utilities.DEFAULT_LONG
import com.onkarnene.synerzip.weather.utilities.EMPTY_STRING
import java.util.concurrent.TimeUnit.*

data class WeatherDetails(
		@SerializedName("coord") var coord: Coord = Coord(),
		@SerializedName("weather") var weather: List<Weather> = listOf(),
		@SerializedName("base") var base: String = EMPTY_STRING,
		@SerializedName("main") var main: Main = Main(),
		@SerializedName("visibility") var visibility: Int = DEFAULT_INT,
		@SerializedName("wind") var wind: Wind = Wind(),
		@SerializedName("rain") var rain: Rain = Rain(),
		@SerializedName("snow") var snow: Snow = Snow(),
		@SerializedName("clouds") var clouds: Clouds = Clouds(),
		@SerializedName("dt") var dt: Long = DEFAULT_LONG,
		@SerializedName("sys") var sys: Sys = Sys(),
		@SerializedName("timezone") var timezone: Long = DEFAULT_LONG,
		@SerializedName("id") var id: Long = System.currentTimeMillis(),
		@SerializedName("name") var name: String = EMPTY_STRING,
		@SerializedName("cod") var cod: Int = DEFAULT_INT,
		val expireAt: Long = System.currentTimeMillis() + MILLISECONDS.convert(24, HOURS)
)