package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT
import com.onkarnene.synerzip.weather.utilities.EMPTY_STRING

data class Weather(
		@SerializedName("id") var id: Int = DEFAULT_INT,
		@SerializedName("main") var main: String = EMPTY_STRING,
		@SerializedName("description") var description: String = EMPTY_STRING,
		@SerializedName("icon") var icon: String = EMPTY_STRING
)