package com.onkarnene.synerzip.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onkarnene.synerzip.weather.models.WeatherDetails

@Dao
interface WeatherDao {
	
	@Query("SELECT * FROM WeatherDetails WHERE name = :name")
	fun getWeatherDetails(name: String): WeatherDetails?
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveOrUpdateWeatherDetails(weatherDetails: WeatherDetails)
	
	@Query("DELETE FROM WeatherDetails WHERE expireAt <= :currentTime")
	fun deleteExpiredWeatherDetails(currentTime: Long = System.currentTimeMillis())
}