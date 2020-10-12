package com.onkarnene.synerzip.weather.views.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.onkarnene.synerzip.weather.interfaces.HttpEventTracker
import com.onkarnene.synerzip.weather.models.Error
import com.onkarnene.synerzip.weather.models.WeatherDetails
import com.onkarnene.synerzip.weather.repository.WeatherRepository
import com.onkarnene.synerzip.weather.views.MainActivity

/**
 * ViewModel implementation for [MainActivity]
 */
class MainViewModel(
		val app: Application,
		private val weatherRepo: WeatherRepository
) : AndroidViewModel(app), HttpEventTracker<WeatherDetails> {
	
	private val weatherDetailsObserver: MutableLiveData<WeatherDetails> = MutableLiveData()
	private val errorObserver: MutableLiveData<Error> = MutableLiveData()
	
	override fun onCallSuccess(response: WeatherDetails) {
		weatherDetailsObserver.postValue(response)
	}
	
	override fun onCallFail(error: Error) {
		errorObserver.postValue(error)
	}
	
	fun clearExpiredWeatherDetails() {
		weatherRepo.clearExpiredItems()
	}
	
	fun searchWeatherDetails(
			query: String,
			forceUpdate: Boolean
	) {
		weatherRepo.searchByCityName(query, forceUpdate, this)
	}
	
	fun getWeatherObserver() = weatherDetailsObserver
	
	fun getErrorObserver() = errorObserver
}