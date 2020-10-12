package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT
import com.onkarnene.synerzip.weather.utilities.EMPTY_STRING

data class Error(
		@SerializedName("cod") var cod: Int = DEFAULT_INT,
		@SerializedName("message") var message: String = EMPTY_STRING,
		var cause: String = EMPTY_STRING
)