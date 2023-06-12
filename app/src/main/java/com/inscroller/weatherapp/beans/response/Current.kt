package com.inscroller.weatherapp.beans.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Current(
    @SerializedName("last_updated_epoch") var lastUpdatedEpoch: Int? = null,
    @SerializedName("last_updated") var lastUpdated: String? = null,
    @SerializedName("temp_c") var tempC: Double? = null,
    @SerializedName("temp_f") var tempF: Double? = null,
    @SerializedName("is_day") var isDay: Int? = null,
    @SerializedName("condition") var condition: Condition? = Condition(),
    @SerializedName("wind_mph") var windMph: Double? = null,
    @SerializedName("wind_kph") var windKph: Double? = null,
    @SerializedName("wind_degree") var windDegree: Int? = null,
    @SerializedName("wind_dir") var windDir: String? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("cloud") var cloud: Int? = null,
) : Serializable
