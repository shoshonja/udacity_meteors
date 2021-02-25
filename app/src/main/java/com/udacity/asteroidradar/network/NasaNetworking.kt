package com.udacity.asteroidradar.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.nasa.gov/"
private const val IMAGE_OF_THE_DAY = "apod?api_key="
private const val NEO_FEED = "neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key="
private const val MY_API_KEY = "cRctHA6uMeloEGbIk979REE430ji8fsI2ZpU5oZW"


val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService {

    @GET(IMAGE_OF_THE_DAY + MY_API_KEY)
    fun getImageOfTheDay():
            Call<String>

    @GET
    fun getNeoFeed()
}

object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}

