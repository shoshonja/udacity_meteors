package com.udacity.asteroidradar.repository

import android.util.Log
import com.udacity.asteroidradar.api.convertAsteroidsToNeos
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.NeoObjectDao
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.utils.Constants.MY_API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class NasaRepository(private val neoObjectDao: NeoObjectDao) {

    suspend fun refreshNeoList() {
        withContext(Dispatchers.IO) {
            Log.d("IVAN", "Fetching asteroids")
            val asteroids =
                NasaApi.retrofitScalarsService.getNeo(getToday(), getToday(), MY_API_KEY).await()
            Log.d("IVAN", "asteroids retrieved: +$asteroids")
            neoObjectDao.insertAll(
                convertAsteroidsToNeos(
                    parseAsteroidsJsonResult(JSONObject(asteroids))
                )
            )

        }
    }

//    private fun createNeoCallback(): Callback<String> {
//        return object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val asteroidArrayList = parseAsteroidsJsonResult(JSONObject(response.body()))
//                neoObjectDao.insertAll(convertAsteroidsToNeos(asteroidArrayList))
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        }
//    }

//    suspend fun getImageOfTheDay(){
//        withContext(Dispatchers.IO){
//            NasaApi.retrofitMoshiService.getImageOfTheDay(MY_API_KEY).awa
//        }
//    }
}