package com.onkarnene.synerzip.weather.utilities

/**
 * Application level constants
 */

const val EMPTY_STRING = ""
const val NA = "N/A"
const val ERROR_UNKNOWN = "Error unknown"
const val DEFAULT_INT = -1
const val DEFAULT_DOUBLE = 0.0
const val DEFAULT_LONG = -1L
const val SUCCESS = "success"
const val FAILURE = "failure"
const val DEGREE_KELVIN = "°K"
const val DEGREE_CELSIUS = "°C"
const val DEGREE_FAHRENHEIT = "°F"
const val METER_PER_SECOND = "m/s"
const val MILES_PER_HOUR = "mph"
const val HECTO_PASCALS = "hPa"
const val PERCENT = "%"
const val MM_PER_HOUR = "mm/h"

enum class Unit(private val value: String) {
	
	/**
	 * Unit for temperature in Kelvin
	 */
	STANDARD("standard"),
	
	/**
	 * Unit for temperature in Celsius
	 */
	METRIC("metric"),
	
	/**
	 * Unit for temperature in Fahrenheit
	 */
	IMPERIAL("imperial");
	
	companion object {
		
		fun findValue(unit: String?): Unit = when (unit) {
			METRIC.value -> METRIC
			IMPERIAL.value -> IMPERIAL
			else -> STANDARD
		}
	}
	
	fun getValue(): String = value
}