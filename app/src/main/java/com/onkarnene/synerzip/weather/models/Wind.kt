package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_DOUBLE
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT

data class Wind(
		@SerializedName("speed") var speed: Double = DEFAULT_DOUBLE,
		@SerializedName("deg") var deg: Int = DEFAULT_INT,
		@SerializedName("gust") var gust: Double = DEFAULT_DOUBLE
)