package com.inscroller.weatherapp.beans.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrentWeatherResponseBean(
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("current") var current: Current? = Current()
) : Serializable