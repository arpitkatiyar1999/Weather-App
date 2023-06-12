package com.inscroller.weatherapp.remote.repo.main

import com.inscroller.weatherapp.beans.response.CurrentWeatherResponseBean
import com.inscroller.weatherapp.utils.SealedResult

interface MainRepo {
    suspend fun getCurrentWeather(apiKey: String, cityName: String, isAql: Boolean): SealedResult<CurrentWeatherResponseBean>
}