package com.benki.weather.network

import com.benki.weather.BuildConfig
import com.benki.weather.network.models.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/forecast.json")
    suspend fun getWeatherUpdates(
        @Query("q") query: String,
        @Query("aqi") airQualityIndex: String = "yes",
        @Query("alerts") alerts: String = "no",
        @Query("days") days: Int = 7,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<WeatherApiResponse>
}