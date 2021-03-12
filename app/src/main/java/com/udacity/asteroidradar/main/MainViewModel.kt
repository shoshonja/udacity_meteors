package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.database.NeoObjectDao
import com.udacity.asteroidradar.database.asAsteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val dataSource: NeoObjectDao) :
    ViewModel() {


    val imageOfTheDayResponse: LiveData<PictureOfDay>
        get() = _imageOfTheDayResponse

    private val _imageOfTheDayResponse = MutableLiveData<PictureOfDay>()

    val neoObjects: LiveData<List<Asteroid>>
        get() = _neoObjects

    private val _neoObjects = MutableLiveData<List<Asteroid>>()

    val neoObjectsRefreshed: LiveData<List<Asteroid>>
        get() = _neoObjectsRefreshed

    private val _neoObjectsRefreshed = MutableLiveData<List<Asteroid>>()

    val navigateToDetails: LiveData<Asteroid>
        get() = _navigateToDetails

    val navigationAvailable: Boolean
        get() = _navigationAvailable

    private var _navigationAvailable: Boolean = false

    private val _navigateToDetails = MutableLiveData<Asteroid>()

    private val repository = NasaRepository(dataSource)


    init {
        getImageOfTheDay()
        getAllNeos()
    }

//    private fun refreshNeoList() {
//        viewModelScope.launch {
//            repository.refreshNeoList()
//        }
//    }

    private fun refreshAndReturnNeoList() {
        viewModelScope.launch {
            _neoObjects.value = repository.refreshAndReturnNeoList().asAsteroid()
        }
    }

    fun getAllNeos() {
        viewModelScope.launch {
            _neoObjects.value = repository.getAllNeos().asAsteroid()
        }
    }

    fun getTodaysNeos() {
        viewModelScope.launch {
            _neoObjects.value = repository.getTodaysNeos(getToday()).asAsteroid()
        }
    }

    fun getSavedNeos() {
        viewModelScope.launch {
            _neoObjects.value = repository.getWeeklyNeos(getNextSevenDaysFormattedDates()).asAsteroid()
        }
    }

    private fun getImageOfTheDay() {
        NasaApi.retrofitMoshiService.getImageOfTheDay(BuildConfig.NASA_API_KEY)
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

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigationAvailable = true
        _navigateToDetails.value = asteroid
        _navigationAvailable = false
    }


    //TODO only triggers if list is empty....
    fun handleTriggeredNeoObjects(it: List<Asteroid>) {
        if (it.isEmpty()) {
            refreshAndReturnNeoList()
        } else {
            _neoObjectsRefreshed.value = it
        }
    }
}


