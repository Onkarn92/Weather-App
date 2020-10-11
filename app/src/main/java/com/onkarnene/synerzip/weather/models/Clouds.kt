package com.onkarnene.synerzip.weather.models

import com.google.gson.annotations.SerializedName
import com.onkarnene.synerzip.weather.utilities.DEFAULT_INT

data class Clouds(
        @SerializedName("all") var all: Int = DEFAULT_INT
)