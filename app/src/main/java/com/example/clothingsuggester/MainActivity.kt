package com.example.clothingsuggester


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.clothingsuggester.data.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var BASE_URL = "api.openweathermap.org"
    private val API_KEY = "5591e9a22ae1fe0c591a03b968c6e3ff"
    private val SCHEME = "https"
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadWeather()

    }

    private fun rquestWeather(): Request {
        val url = HttpUrl.Builder()
            .scheme(SCHEME)
            .host(BASE_URL)
            .addPathSegment("data/2.5/weather")
            .addQueryParameter("lat", "31.5019")
            .addQueryParameter("lon", "34.4666")
            .addQueryParameter("units", "metric")
            .addQueryParameter("appid", API_KEY)
            .build()
        return Request.Builder().url(url).build()
    }

    private fun loadWeather() {
        client.newCall(rquestWeather()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().toString().let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherData::class.java)
                    binding.run {
                        runOnUiThread {
                            val Temperature=result.main.temp
                            textCity.text = result.name
                            textDate.text=formatTimestamp(result.dt)
                            textTemperature.text = "$Temperature°C"
                           imageClothes.setImageResource(getOneClothe(Temperature)[0])
                            val imgWeather = result.weather.get(0).icon
                            Glide.with(applicationContext)
                                .load("https://openweathermap.org/img/w/$imgWeather.png")
                                .into(binding.imgWeather)

                        }
                    }

                }


            }

        })
    }


    fun getOneClothe( temp:Double):List<Int>{
       if (temp<=15)
           return getColdClothesImage()

        else if (temp in 15.0..25.0)
           return getNormalClothesImage()

        else  return getHotClothesImage()
    }
    fun getColdClothesImage(): List<Int> {
        val imageIdList = mutableListOf<Int>()
        imageIdList.add(R.drawable.img_cold_1)
        imageIdList.add(R.drawable.img_cold_2)
        imageIdList.add(R.drawable.img_cold_3)

        return imageIdList.shuffled()
    }

    fun getNormalClothesImage(): List<Int> {
        val imageIdList = mutableListOf<Int>()
        imageIdList.add(R.drawable.img_normal_1)
        imageIdList.add(R.drawable.img_normal_2)
        imageIdList.add(R.drawable.img_normal_3)

        return imageIdList.shuffled()
    }

    fun getHotClothesImage(): List<Int> {
        val imageIdList = mutableListOf<Int>()
        imageIdList.add(R.drawable.img_hot_1)
        imageIdList.add(R.drawable.img_hot_2)
        imageIdList.add(R.drawable.img_hot_3)

        return imageIdList.shuffled()
    }

    fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault())
        return format.format(date)
    }

    
}
