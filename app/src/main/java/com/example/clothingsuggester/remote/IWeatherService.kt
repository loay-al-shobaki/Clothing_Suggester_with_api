package com.example.clothingsuggester.remote

import com.example.clothingsuggester.model.Weather
import com.example.clothingsuggester.model.WeatherData
import okio.IOException

interface IWeatherService {

    fun getCurrentWeatherService(
        onGetCurrentWetherSuccess:(WeatherData)-> Unit,
        onGetCurrentWetherFailure:(IOException)->Unit
    )
}