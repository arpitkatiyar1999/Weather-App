package com.inscroller.weatherapp.beans.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Condition(
    @SerializedName("text") var text: String? = null,
    @SerializedName("icon") var icon: String? = null,
) : Serializable