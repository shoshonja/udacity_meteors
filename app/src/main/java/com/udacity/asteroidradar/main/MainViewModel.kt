package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.NeoObjectDao
import com.udacity.asteroidradar.database.asAsteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.repository.NasaRepository
import com.udacity.asteroidradar.utils.Constants.MY_API_KEY
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val dataSource: NeoObjectDao, val application: Application) : ViewModel() {

    //TODO get image of the day via retrofit
    //TODO get asteroid data via retrofit
    //TODO save data in the database
    //TODO display data via recycler view
    //TODO on click of the recycler view item, navigate to the fragment details
    //TODO pass data with safeargs to the fragment detail


    val imageOfTheDayResponse: LiveData<PictureOfDay>
        get() = _imageOfTheDayResponse

    private val _imageOfTheDayResponse = MutableLiveData<PictureOfDay>()

    val neoObjects: LiveData<List<Asteroid>>
        get() = _neoObjects

    private val _neoObjects = MutableLiveData<List<Asteroid>>()

    private val repository = NasaRepository(dataSource)


    init {
        getImageOfTheDay()


        viewModelScope.launch {
            repository.refreshNeoList()
        }


        viewModelScope.launch {
            _neoObjects.value = repository.getAllNeos().asAsteroid()
            Log.d("IVAN", "neoObjects populated: " + _neoObjects.value?.size)
        }
    }


    private fun getImageOfTheDay() {
        NasaApi.retrofitMoshiService.getImageOfTheDay(MY_API_KEY)
            .enqueue(createImageOfTheDayCallback())
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
}


