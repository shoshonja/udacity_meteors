package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.utils.Constants.API_KEY
import com.udacity.asteroidradar.utils.Constants.BASE_URL
import com.udacity.asteroidradar.utils.Constants.END_DATE
import com.udacity.asteroidradar.utils.Constants.IMAGE_OF_THE_DAY
import com.udacity.asteroidradar.utils.Constants.MY_API_KEY
import com.udacity.asteroidradar.utils.Constants.NEO_FEED
import com.udacity.asteroidradar.utils.Constants.START_DATE
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofitWithMoshi: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

private val retrofitWithScalars: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface NasaApiMoshiService {
    @GET(IMAGE_OF_THE_DAY)
    fun getImageOfTheDay(
        @Query(API_KEY) myKey: String
    ):
            Call<PictureOfDay>
}

interface NasaApiScalarsService {
    @GET(NEO_FEED)
    fun getNeo(
        @Query(START_DATE) startDate: String,
        @Query(END_DATE) endDate: String,
        @Query(API_KEY) myKey: String

    ):
            Call<String>
}

object NasaApi {
    val retrofitMoshiService: NasaApiMoshiService by lazy {
        retrofitWithMoshi.create(NasaApiMoshiService::class.java)
    }

    val retrofitScalarsService: NasaApiScalarsService by lazy {
        retrofitWithScalars.create(NasaApiScalarsService::class.java)
    }
}

