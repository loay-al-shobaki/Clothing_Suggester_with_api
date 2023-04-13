package com.example.clothingsuggester.LocalData

import com.example.clothingsuggester.R
import java.text.SimpleDateFormat
import java.util.*

class LocalData{
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