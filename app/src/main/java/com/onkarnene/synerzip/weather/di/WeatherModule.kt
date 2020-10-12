package com.onkarnene.synerzip.weather.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.onkarnene.synerzip.weather.repository.WeatherRepository
import com.onkarnene.synerzip.weather.views.models.Factory
import com.onkarnene.synerzip.weather.views.models.MainViewModel
import dagger.Module
import dagger.Provides

/**
 * Dagger module implementation for weather data.
 */
@Module
class WeatherModule {
	
	@Provides
	fun provideWeatherRepository(): WeatherRepository = WeatherRepository()
	
	@Provides
	fun provideMainViewModel(
			owner: ViewModelStoreOwner,
			repository: WeatherRepository
	): MainViewModel {
		return ViewModelProvider(owner, Factory(repository))[MainViewModel::class.java]
	}
}