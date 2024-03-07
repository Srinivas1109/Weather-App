package com.benki.weather.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.benki.weather.network.WeatherApi
import com.benki.weather.network.models.ErrorResponse
import com.benki.weather.network.models.NetworkResponse
import com.benki.weather.network.models.WeatherApiResponse
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherRepositoryImpl(private val weatherApi: WeatherApi, private val context: Context) :
    WeatherRepository {
    private val _networkResponse: MutableStateFlow<NetworkResponse> =
        MutableStateFlow(NetworkResponse.Idle)
    override val networkResponse: StateFlow<NetworkResponse> = _networkResponse.asStateFlow()
    override suspend fun getWeatherUpdates(
        query: String,
        airQualityIndex: String
    ) {
        _networkResponse.update {
            NetworkResponse.Loading
        }
        try {
            val response: Response<WeatherApiResponse> =
                weatherApi.getWeatherUpdates(query, airQualityIndex)
            if (response.isSuccessful && response.body() != null) {
                _networkResponse.update {
                    NetworkResponse.Success(data = response.body()!!)
                }
            } else if (response.errorBody() != null) {
                val errorBody = response.errorBody()?.charStream()?.readText()
                if (errorBody != null) {
                    val jsonResponse = JSONObject(errorBody)
                    val error = jsonResponse.getJSONObject("error")
                    val code = error.getInt("code")
                    val message = error.getString("message")
                    _networkResponse.update {
                        NetworkResponse.Error(
                            data = ErrorResponse(
                                code = code,
                                message = message
                            )
                        )
                    }
                }
            } else {
                _networkResponse.update {
                    NetworkResponse.Error(
                        data = ErrorResponse(
                            code = 500,
                            message = "Internal Server Error!"
                        )
                    )
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.charStream()?.readText()
            if (errorBody != null) {
                val jsonResponse = JSONObject(errorBody)
                val error = jsonResponse.getJSONObject("error")
                val code = error.getInt("code")
                val message = error.getString("message")
                _networkResponse.update {
                    NetworkResponse.Error(
                        data = ErrorResponse(
                            code = code,
                            message = message
                        )
                    )
                }
            } else {
                _networkResponse.update {
                    NetworkResponse.Error(
                        data = ErrorResponse(
                            code = 500,
                            message = "Internal Server Error!"
                        )
                    )
                }
            }
        }
    }

    override suspend fun getDeviceLastLocation(): Location? = suspendCoroutine { continuation ->
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            println("DEVICE_LOCATION: PERMISSION DENIED")
            continuation.resume(null)
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                println("DEVICE_LOCATION: PERMISSION GRANTED: $location")
                continuation.resume(location)
            }
        }
    }

    override suspend fun getWorkerWeatherUpdates(
        query: String,
        airQualityIndex: String
    ): WeatherApiResponse? {
        return try {
            val response = weatherApi.getWeatherUpdates(query, "yes")
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }


}