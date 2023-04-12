package com.example.clothingsuggester


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.clothingsuggester.model.WeatherData
import com.example.clothingsuggester.databinding.ActivityMainBinding
import com.example.clothingsuggester.presienter.MainPresenter
import okio.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), IMainView {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val presenter = MainPresenter(this)
        presenter.getCurrentWether()

    }


    override fun onGetWEtherState(weatherResponse: WeatherData) {
        binding.run {
            runOnUiThread {
                val Temperature = weatherResponse.main.temp
                textCity.text = weatherResponse.name
                textDate.text = formatTimestamp(weatherResponse.dt)
                textTemperature.text = "$Temperature°C"
                imageClothes.setImageResource(getOneClothe(Temperature)[0])
                val imgWeather = weatherResponse.weather.get(0).icon
                Glide.with(applicationContext)
                    .load("https://openweathermap.org/img/w/$imgWeather.png")
                    .into(binding.imgWeather)
            }
        }
    }

    override fun onGetFailure(exception: okio.IOException) {

    }


    fun getOneClothe(temp: Double): List<Int> {
        if (temp <= 15)
            return getColdClothesImage()
        else if (temp in 15.0..25.0)
            return getNormalClothesImage()
        else return getHotClothesImage()
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
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }


}

