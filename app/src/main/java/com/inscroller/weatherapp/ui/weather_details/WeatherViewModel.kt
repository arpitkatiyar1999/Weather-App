package com.inscroller.weatherapp.ui.weather_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.inscroller.weatherapp.base.BaseViewModel
import com.inscroller.weatherapp.beans.response.CurrentWeatherResponseBean
import com.inscroller.weatherapp.remote.repo.main.MainRepo
import com.inscroller.weatherapp.utils.Constants
import com.inscroller.weatherapp.utils.Event
import com.inscroller.weatherapp.utils.SealedResult
import com.inscroller.weatherapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor() : BaseViewModel() {
    lateinit var currentWeatherResponseBean: CurrentWeatherResponseBean

}