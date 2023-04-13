package com.example.clothingsuggester


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.clothingsuggester.LocalData.LocalData
import com.example.clothingsuggester.model.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.presienter.MainPresenter


class MainActivity : AppCompatActivity(), IMainView {
    lateinit var binding: ActivityMainBinding
     lateinit var localData:LocalData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val presenter = MainPresenter(this)
        presenter.getCurrentWether()

    }


    override fun onGetWEtherState(weatherResponse: WeatherData) {
        localData= LocalData()
        binding.run {
            runOnUiThread {
                val Temperature = weatherResponse.main.temp
                textCity.text = weatherResponse.name
              textDate.text = localData.formatTimestamp(weatherResponse.dt)
                textTemperature.text = "$Temperature°C"
            imageClothes.setImageResource(localData.getOneClothe(Temperature)[0])
                val imgWeather = weatherResponse.weather.get(0).icon
                Glide.with(applicationContext)
                    .load("https://openweathermap.org/img/w/$imgWeather.png")
                    .into(binding.imgWeather)
            }
        }
    }

    override fun onGetFailure(exception: okio.IOException) {
         Log.i("loay", exception.toString())
    }





}

