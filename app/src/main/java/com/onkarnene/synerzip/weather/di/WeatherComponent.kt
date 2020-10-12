package com.onkarnene.synerzip.weather.di

import androidx.lifecycle.ViewModelStoreOwner
import com.onkarnene.synerzip.weather.repository.WeatherRepository
import com.onkarnene.synerzip.weather.views.MainActivity
import com.onkarnene.synerzip.weather.views.models.MainViewModel
import dagger.BindsInstance
import dagger.Component

/**
 * Dagger component implementation for Weather data
 */
@Component(modules = [WeatherModule::class])
interface WeatherComponent {
	
	/**
	 * Inject [MainActivity] instance into dagger component.
	 */
	fun injectMainActivity(mainActivity: MainActivity)
	
	/**
	 * Provides [MainViewModel] instance.
	 */
	fun getMainViewModel(): MainViewModel
	
	/**
	 * Provides [WeatherRepository] instance used by view model factory.
	 */
	fun getWeatherRepository(): WeatherRepository
	
	@Component.Builder
	interface Builder {
		
		@BindsInstance
		fun withOwner(owner: ViewModelStoreOwner): Builder
		
		fun build(): WeatherComponent
	}
}