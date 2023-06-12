package com.inscroller.weatherapp.remote.repo.main

import com.inscroller.weatherapp.beans.response.CurrentWeatherResponseBean
import com.inscroller.weatherapp.remote.ApiService
import com.inscroller.weatherapp.utils.SealedResult
import javax.inject.Inject

class MainRepoImpl @Inject constructor(private val apiService: ApiService) : MainRepo {
    override suspend fun getCurrentWeather(apiKey: String, cityName: String, isAql: Boolean): SealedResult<CurrentWeatherResponseBean> {
        return try {
            val result = apiService.getCurrentWeatherAsync(apiKey, cityName, isAql).await()
            SealedResult.success(result)
        } catch (exception: Exception) {
            SealedResult.error(exception)
        }
    }
}