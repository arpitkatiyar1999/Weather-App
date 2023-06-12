package com.inscroller.weatherapp.remote

import com.inscroller.weatherapp.beans.response.CurrentWeatherResponseBean
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("current.json")
    fun getCurrentWeatherAsync(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("aqi") aqi: Boolean,
    ): Deferred<CurrentWeatherResponseBean>
}