package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    //TODO get image of the day via retrofit
    //TODO get asteroid data via retrofit
    //TODO save data in the database
    //TODO display data via recycler view
    //TODO on click of the recycler view item, navigate to the fragment details
    //TODO pass data with safeargs to the fragment detail

    val imageOfTheDayResponse: LiveData<PictureOfDay>
        get() = _imageOfTheDayResponse

    private val _imageOfTheDayResponse = MutableLiveData<PictureOfDay>()


    val neoObjectResponse: LiveData<String>
        get() = _newObjectResponse

    private val _newObjectResponse = MutableLiveData<String>()

    init {
        getImageOfTheDay()
//        getNeoObjects()
    }


    private fun getImageOfTheDay() {
        NasaApi.retrofitService.getImageOfTheDay().enqueue(createImageOfTheDayCallback())
    }

    private fun createImageOfTheDayCallback(): retrofit2.Callback<PictureOfDay> {
        return object : retrofit2.Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                _imageOfTheDayResponse.value = response.body()
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
            }
        }
    }

    private fun getNeoObjects() {
        //TODO: get not implemented properly
        NasaApi.retrofitService.getNeoFeed()
    }
}


