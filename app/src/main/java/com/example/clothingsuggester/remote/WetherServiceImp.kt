package com.example.clothingsuggester.remote

import com.example.clothingsuggester.model.WeatherData
import com.google.gson.Gson
import okhttp3.*
import okio.IOException


class WetherServiceImp() : IWeatherService {

    private val client = OkHttpClient()

    companion object {
        private var BASE_URL = "api.openweathermap.org"
        private val API_Id = "appid"
        private val API_KEY_VALUE = "5591e9a22ae1fe0c591a03b968c6e3ff"
        private val SCHEME = "https"
        private val LAT = "lat"
        private val LAT_VALUE = "31.5019"
        private val LON = "lon"
        private val LON_VALUE = "34.4666"
        private val UNITS = "units"
        private val PATH_SEGMENTh = "data/2.5/weather"

    }

    private fun rquestWeather(): Request {
        val url = HttpUrl.Builder()
            .scheme(SCHEME)
            .host(BASE_URL)
            .addPathSegment(PATH_SEGMENTh)
            .addQueryParameter(LAT, LAT_VALUE)
            .addQueryParameter(LON, LON_VALUE)
            .addQueryParameter(UNITS, "metric")
            .addQueryParameter(API_Id, API_KEY_VALUE)
            .build()
        return Request.Builder().url(url).build()
    }

    override fun getCurrentWeatherService(
        onGetCurrentWetherSuccess: (WeatherData) -> Unit,
        onGetCurrentWetherFailure: (okio.IOException) -> Unit
    ) {

        val url = HttpUrl.Builder()
        client.newCall(rquestWeather()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
              onGetCurrentWetherFailure(e)
            }
            override fun onResponse(call: Call, response: Response) {
              val body= response.body?.string()
                val result =Gson().fromJson(body,WeatherData::class.java)
                onGetCurrentWetherSuccess(result)

            }

        })

}

}
