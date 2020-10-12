package com.onkarnene.synerzip.weather.repository

import com.onkarnene.synerzip.weather.database.AppDatabase
import com.onkarnene.synerzip.weather.helpers.AppExecutors
import com.onkarnene.synerzip.weather.interfaces.APIService
import com.onkarnene.synerzip.weather.interfaces.HttpEventTracker
import com.onkarnene.synerzip.weather.interfaces.HttpOperationCallback
import com.onkarnene.synerzip.weather.models.Error
import com.onkarnene.synerzip.weather.models.WeatherDetails
import com.onkarnene.synerzip.weather.network.HttpOperationWrapper
import com.onkarnene.synerzip.weather.utilities.NetworkUtils
import retrofit2.Call

/**
 * Repository to manage communication with network and database layer.
 */
class WeatherRepository : HttpOperationCallback<WeatherDetails> {
	
	private val apiService by lazy {NetworkUtils.retrofit.create(APIService::class.java)}
	private val httpOperationWrapper by lazy {HttpOperationWrapper<WeatherDetails>()}
	private var weatherEventTracker: HttpEventTracker<WeatherDetails>? = null
	
	override fun onResponse(
			call: Call<WeatherDetails>,
			result: WeatherDetails?,
			error: Error
	) {
		when {
			result != null && result.name.isNotBlank() -> {
				saveWeatherDetails(result)
				weatherEventTracker?.onCallSuccess(result)
			}
			else -> weatherEventTracker?.onCallFail(error)
		}
	}
	
	fun searchByCityName(
			query: String,
			callback: HttpEventTracker<WeatherDetails>
	) {
		weatherEventTracker = callback
		AppExecutors.diskIO.execute {
			val splitQuery = query.split(",".toRegex()).first()
			val weatherDetails: WeatherDetails? = AppDatabase.getInstance().weatherDao().getWeatherDetails(splitQuery)
			if (weatherDetails != null) {
				weatherEventTracker?.onCallSuccess(weatherDetails)
			} else {
				httpOperationWrapper.initCall(apiService.searchWeather(query), this)
			}
		}
	}
	
	fun clearExpiredItems() {
		AppExecutors.diskIO.execute {
			AppDatabase.getInstance().weatherDao().deleteExpiredWeatherDetails()
		}
	}
	
	private fun saveWeatherDetails(weatherDetails: WeatherDetails) {
		AppExecutors.diskIO.execute {
			AppDatabase.getInstance().weatherDao().saveOrUpdateWeatherDetails(weatherDetails)
		}
	}
}