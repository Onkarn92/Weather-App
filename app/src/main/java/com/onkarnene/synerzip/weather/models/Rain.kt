package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_DOUBLE

data class Rain(
		@SerializedName("1h") var oneHour: Double = DEFAULT_DOUBLE,
		@SerializedName("3h") var threeHour: Double = DEFAULT_DOUBLE
)