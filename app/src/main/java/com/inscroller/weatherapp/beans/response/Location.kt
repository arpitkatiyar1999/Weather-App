package com.inscroller.weatherapp.beans.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null,
) : Serializable