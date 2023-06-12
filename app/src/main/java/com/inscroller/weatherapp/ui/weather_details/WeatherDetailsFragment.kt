package com.inscroller.weatherapp.ui.weather_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.inscroller.weatherapp.R
import com.inscroller.weatherapp.base.BaseFragment
import com.inscroller.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.inscroller.weatherapp.utils.TemperatureUnits
import com.inscroller.weatherapp.utils.WindUnits
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment<WeatherViewModel>() {
    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WeatherDetailsFragmentArgs>()
    override fun setViewModel(): WeatherViewModel {
        return ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentWeatherResponseBean = args.currentWeatherCondition
        initUi()
        initListener()
    }

    private fun initListener() {
        with(binding) {
            temperatureUnitTv.setOnClickListener {
                if (sharedPref.currentTemperatureUnit == TemperatureUnits.CELSIUS.value) {
                    sharedPref.currentTemperatureUnit = TemperatureUnits.FAHRENHEIT.value
                } else {
                    sharedPref.currentTemperatureUnit = TemperatureUnits.CELSIUS.value
                }
                setCurrentTemperature()
            }
            windSpeedTxtUnitTv.setOnClickListener {
                if (sharedPref.currentWindUnit == WindUnits.MPH.value) {
                    sharedPref.currentWindUnit = WindUnits.KPH.value
                } else {
                    sharedPref.currentWindUnit = WindUnits.MPH.value
                }
                setWindSpeed()
            }
        }
    }

    private fun initUi() {
        with(binding) {
            viewModel.currentWeatherResponseBean.apply {
                if (this.current?.isDay == 1) {
                    bgIv.setImageResource(R.drawable.day_sky)
                } else {
                    bgIv.setImageResource(R.drawable.night_sky)
                }
                setCurrentTemperature()
                setWindSpeed()
                locationTv.text = getString(R.string.string_string_placeholder, this.location?.name, this.location?.country)
                val url = "http:${this.current?.condition?.icon}"
                Glide.with(currentTempImg).load(url).into(currentTempImg)
                currentConditionTv.text = this.current?.condition?.text
                cloudTv.text = getString(R.string.string_string__without_comma_placeholder, this.current?.cloud.toString(), "%")
                humidityTv.text = getString(R.string.string_string__without_comma_placeholder, this.current?.humidity.toString(), "%")
            }
        }
    }

    private fun setCurrentTemperature() {
        with(binding) {
            if (sharedPref.currentTemperatureUnit == TemperatureUnits.CELSIUS.value) {
                currentTemperatureTv.text = viewModel.currentWeatherResponseBean.current?.tempC.toString()
                currentTemperatureUnitTv.text = getString(R.string.c)
                temperatureUnitTv.text = getString(R.string.celsius)
            } else {
                currentTemperatureTv.text = viewModel.currentWeatherResponseBean.current?.tempF.toString()
                currentTemperatureUnitTv.text = getString(R.string.f)
                temperatureUnitTv.text = getString(R.string.fahrenheit)
            }
        }
    }

    private fun setWindSpeed() {
        with(binding) {
            if (sharedPref.currentWindUnit == WindUnits.MPH.value) {
                val windText = "${viewModel.currentWeatherResponseBean.current?.windMph} ${viewModel.currentWeatherResponseBean.current?.windDir}"
                windSpeedTv.text = windText
                windSpeedTxtUnitTv.text = getString(R.string.miles_hrs)
            } else {
                val windText = "${viewModel.currentWeatherResponseBean.current?.windKph} ${viewModel.currentWeatherResponseBean.current?.windDir}"
                windSpeedTv.text = windText
                windSpeedTxtUnitTv.text = getString(R.string.kilometer_hrs)
            }
        }
    }
}