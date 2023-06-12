package com.inscroller.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPref @Inject constructor(val context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    companion object {
        const val SHARED_PREF_NAME = "com.inscroller.weatherapp.shared_pref"
        const val TEMPERATURE_UNIT = "temperature_unit"
        const val WIND_UNIT = "wind_unit"
    }

    var currentTemperatureUnit: String
        get() = sharedPref.getString(TEMPERATURE_UNIT, TemperatureUnits.CELSIUS.value) ?: TemperatureUnits.CELSIUS.value
        set(value) {
            editor.putString(TEMPERATURE_UNIT, value)
            editor.apply()
        }
    var currentWindUnit: String
        get() = sharedPref.getString(TEMPERATURE_UNIT, WindUnits.MPH.value) ?: WindUnits.MPH.value
        set(value) {
            editor.putString(TEMPERATURE_UNIT, value)
            editor.apply()
        }
}