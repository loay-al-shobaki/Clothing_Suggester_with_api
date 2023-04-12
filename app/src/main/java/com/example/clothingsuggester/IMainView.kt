package com.example.clothingsuggester

import com.example.clothingsuggester.model.WeatherData
import okio.IOException

interface IMainView {
    fun onGetWEtherState(weatherResponse: WeatherData)
    fun onGetFailure(exception:IOException)
}