package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.NeoObjectDao
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.utils.Constants.MY_API_KEY
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(dataSource: NeoObjectDao, application: Application) : ViewModel() {

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
        getNeoObjects()
    }


    private fun getImageOfTheDay() {
        NasaApi.retrofitMoshiService.getImageOfTheDay(MY_API_KEY).enqueue(createImageOfTheDayCallback())
    }

    private fun createImageOfTheDayCallback(): Callback<PictureOfDay> {
        return object : Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                _imageOfTheDayResponse.value = response.body()
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
            }
        }
    }

    private fun getNeoObjects() {
        NasaApi.retrofitScalarsService.getNeo(getToday(), getToday(), MY_API_KEY).enqueue(createNeoCallback())
    }

    private fun createNeoCallback(): Callback<String>? {
        return object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val asteroidArrayList = parseAsteroidsJsonResult(JSONObject(response.body()))
//                _newObjectResponse.value = response.body()
                //for loop and store in database
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
    }
}


