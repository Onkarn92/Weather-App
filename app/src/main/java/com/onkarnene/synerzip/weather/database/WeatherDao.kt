package com.onkarnene.synerzip.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.onkarnene.synerzip.weather.models.WeatherDetails

@Dao
interface WeatherDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveOrUpdateWeather(weatherDetails: WeatherDetails)
}