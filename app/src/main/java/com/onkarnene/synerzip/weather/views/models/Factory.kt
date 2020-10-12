package com.onkarnene.synerzip.weather.views.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onkarnene.synerzip.weather.App
import com.onkarnene.synerzip.weather.repository.WeatherRepository

class Factory(private val weatherRepository: WeatherRepository) : ViewModelProvider.NewInstanceFactory() {
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return when {
			modelClass.isAssignableFrom(MainViewModel::class.java) -> {
				(MainViewModel(App.getApplication(), weatherRepository) as T)
			}
			else -> super.create(modelClass)
		}
	}
}