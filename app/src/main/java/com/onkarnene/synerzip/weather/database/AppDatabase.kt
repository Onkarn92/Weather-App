package com.onkarnene.synerzip.weather.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.onkarnene.synerzip.weather.App
import com.onkarnene.synerzip.weather.models.WeatherDetails

/**
 * Room persistence database for weather app.
 */
@Database(entities = [WeatherDetails::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {
	
	companion object {
		
		private const val NAME = "weather_db"
		private val lock = Any()
		@Volatile private var appDatabase: AppDatabase? = null
		
		/**
		 * Provide and maintain single instance.
		 */
		fun getInstance(): AppDatabase = appDatabase ?: synchronized(lock) {
			appDatabase ?: buildDatabase().also {appDatabase = it}
		}
		
		private fun buildDatabase(): AppDatabase = Room.databaseBuilder(App.getContext(), AppDatabase::class.java, NAME).apply {
			fallbackToDestructiveMigration()
		}.build()
	}
	
	/**
	 * Data access object for weather entity.
	 */
	abstract fun weatherDao(): WeatherDao
}