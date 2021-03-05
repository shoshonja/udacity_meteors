package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.NeoObjectDao
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.repository.NasaRepository
import com.udacity.asteroidradar.utils.Constants.MY_API_KEY
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val dataSource: NeoObjectDao, val application: Application) : ViewModel() {

    //TODO get image of the day via retrofit
    //TODO get asteroid data via retrofit
    //TODO save data in the database
    //TODO display data via recycler view
    //TODO on click of the recycler view item, navigate to the fragment details
    //TODO pass data with safeargs to the fragment detail

    val imageOfTheDayResponse: LiveData<PictureOfDay>
        get() = _imageOfTheDayResponse

    private val _imageOfTheDayResponse = MutableLiveData<PictureOfDay>()

    private val repository = NasaRepository(dataSource)

//    val neoObjectResponse: LiveData<String>
//        get() = _newObjectResponse
//
//    private val _newObjectResponse = MutableLiveData<String>()
//
//    private lateinit var todayNeos: LiveData<List<Asteroid>>


//    val asteroids: LiveData<List<Asteroid>>
//        get() = _asteroids
//
//    private val _asteroids = MutableLiveData<List<Asteroid>>()


    init {
        getImageOfTheDay()
//        getNeoObjects()
        viewModelScope.launch {
            repository.refreshNeoList()
        }
    }

//    private fun refreshDataFromNetwork() = viewModelScope.launch {
//        NasaApi.retrofitScalarsService.getNeo(getToday(), getToday(), MY_API_KEY)
//            .enqueue(createGetNeoCallback())
//    }
//
//    private fun createGetNeoCallback(): Callback<String> {
//        return object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                _asteroids.value = parseAsteroidsJsonResult(JSONObject(response.body()))
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        }
//    }


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

//    private fun getNeoObjects() {
//        val neos = dataSource.getTodaysNeo(getToday())
//        if (neos.value != null) {
//
//        } else {
//            getNeoObjectsFromWeb()
//        }
//        //convert neoObject to asteroid
//        //try to get local
//        //if it fail, get from web.
//    }
//
//
//    //enque goes on background thread
//    private fun getNeoObjectsFromWeb() {
//        NasaApi.retrofitScalarsService.getNeo(getToday(), getToday(), MY_API_KEY)
//            .enqueue(createNeoCallback())
//    }

//    private fun createNeoCallback(): Callback<String>? {
//        return object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val asteroidArrayList = parseAsteroidsJsonResult(JSONObject(response.body()))
//                _newObjectResponse.value = response.body()
////                if (!asteroidArrayList.isEmpty()){
////                    for (asteroid in asteroidArrayList){
////                        insert(convertAsteroidToNeo(asteroid))
////                    }
////                }
//                //for loop and store in database
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        }
//    }

//    private suspend fun insert(neoObject: NeoObject){
//        dataSource.insert(neoObject)
//    }
}


