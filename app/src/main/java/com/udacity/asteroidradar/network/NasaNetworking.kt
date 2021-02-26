package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.models.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.nasa.gov/"
private const val IMAGE_OF_THE_DAY = "planetary/apod?api_key="
private const val NEO_FEED = "neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key="
private const val MY_API_KEY = "cRctHA6uMeloEGbIk979REE430ji8fsI2ZpU5oZW"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

//     .addConverterFactory(ScalarsConverterFactory.create())
//      this converts to String

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService {

    @GET(IMAGE_OF_THE_DAY + MY_API_KEY)
    fun getImageOfTheDay():
            Call<PictureOfDay>

    @GET
    fun getNeoFeed()
}

object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}

