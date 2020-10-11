package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT
import com.onkarnene.synerzip.weather.utilities.DEFAULT_LONG
import com.onkarnene.synerzip.weather.utilities.EMPTY_STRING

data class Sys(
		@SerializedName("type") var type: Int = DEFAULT_INT,
		@SerializedName("id") var id: Int = DEFAULT_INT,
		@SerializedName("country") var country: String = EMPTY_STRING,
		@SerializedName("sunrise") var sunrise: Long = DEFAULT_LONG,
		@SerializedName("sunset") var sunset: Long = DEFAULT_LONG
)