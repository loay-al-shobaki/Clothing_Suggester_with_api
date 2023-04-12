package com.example.clothingsuggester.presienter

import com.example.clothingsuggester.IMainView
import com.example.clothingsuggester.model.WeatherData
import com.example.clothingsuggester.remote.WetherServiceImp
import okio.IOException

class MainPresenter(private  var iMainView: IMainView) {
    private  val wetherServiceImp = WetherServiceImp()


    fun onGetResponse(wetherResponse:WeatherData)=
        iMainView.onGetWEtherState(wetherResponse)

    fun onGetFailure(exception:IOException)=
       iMainView.onGetFailure(exception)

    fun getCurrentWether(){
        wetherServiceImp.getCurrentWeatherService(::onGetResponse,::onGetFailure)
    }
}