package com.onkarnene.synerzip.weather.interfaces

import com.onkarnene.synerzip.weather.models.WeatherDetails
import com.onkarnene.synerzip.weather.utilities.NetworkUtils
import com.onkarnene.synerzip.weather.utilities.Unit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * OpenWeatherMap API service used by Retrofit.
 */
interface APIService {
	
	@GET(NetworkUtils.ENDPOINT_WEATHER_BY_CITY_NAME)
	fun searchWeather(
			@Query("q") query: String,
			@Query("units") units: String = Unit.STANDARD.getValue(),
			@Query("appid") appId: String = NetworkUtils.APP_ID
	): Call<WeatherDetails>
}