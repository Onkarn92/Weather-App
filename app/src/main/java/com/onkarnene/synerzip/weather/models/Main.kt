package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_DOUBLE
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT

data class Main(
		@SerializedName("temp") var temp: Double = DEFAULT_DOUBLE,
		@SerializedName("feels_like") var feelsLike: Double = DEFAULT_DOUBLE,
		@SerializedName("temp_min") var tempMin: Double = DEFAULT_DOUBLE,
		@SerializedName("temp_max") var tempMax: Double = DEFAULT_DOUBLE,
		@SerializedName("pressure") var pressure: Int = DEFAULT_INT,
		@SerializedName("humidity") var humidity: Int = DEFAULT_INT,
		@SerializedName("sea_level") var seaLevel: Int = DEFAULT_INT,
		@SerializedName("grnd_level") var grndLevel: Int = DEFAULT_INT
)