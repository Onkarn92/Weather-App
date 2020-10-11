package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_DOUBLE

data class Coord(
		@SerializedName("lon") var lon: Double = DEFAULT_DOUBLE,
		@SerializedName("lat") var lat: Double = DEFAULT_DOUBLE
)