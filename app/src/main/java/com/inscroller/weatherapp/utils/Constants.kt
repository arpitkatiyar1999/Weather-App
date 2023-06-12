package com.inscroller.weatherapp.utils

import android.Manifest

object Constants {
    val permissionList = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    const val API_KEY = "8e9a9becf1c6481ca14103427230605"
    const val BASE_URL = "http://api.weatherapi.com/v1/"
}