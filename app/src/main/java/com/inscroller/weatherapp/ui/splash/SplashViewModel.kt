package com.inscroller.weatherapp.ui.splash

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.LocationManager
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
class SplashViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val geocoder: Geocoder,
    private val repo: MainRepo
) : BaseViewModel() {
    private val _getLocationLiveData = MutableLiveData<Event<State<String>>>()
    val getLocationLiveData: LiveData<Event<State<String>>> get() = _getLocationLiveData
    private val _currentWeatherLiveData = MutableLiveData<Event<State<CurrentWeatherResponseBean>>>()
    val currentWeatherLiveData: LiveData<Event<State<CurrentWeatherResponseBean>>> get() = _currentWeatherLiveData

    @SuppressLint("MissingPermission")
    fun getCurrentCity() {
        _getLocationLiveData.postValue(Event(State.loading()))
        viewModelScope.launch {
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (lastKnownLocation != null) {
                val latitude = lastKnownLocation.latitude
                val longitude = lastKnownLocation.longitude
                val coordinates = "$latitude,$longitude"
                _getLocationLiveData.postValue(Event(State.success(coordinates)))
            } else {
                _getLocationLiveData.postValue(Event(State.error("Unable to fetch current location", Throwable())))
            }
        }
    }

    fun getCurrentWeather(currentCity: String) {
        _currentWeatherLiveData.postValue(Event(State.loading()))
        viewModelScope.launch {
            when (val response = repo.getCurrentWeather(Constants.API_KEY, currentCity, false)) {
                is SealedResult.Success -> {
                    _currentWeatherLiveData.postValue(Event(State.success(response.data)))
                }

                is SealedResult.Error -> {
                    _currentWeatherLiveData.postValue(
                        Event(
                            State.error(
                                response.exception.localizedMessage ?: "Some unknown error occurred",
                                response.exception
                            )
                        )
                    )
                }
            }
        }
    }
}