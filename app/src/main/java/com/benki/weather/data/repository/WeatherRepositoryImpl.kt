package com.benki.weather.data.repository

import com.benki.weather.network.WeatherApi
import com.benki.weather.network.models.ErrorResponse
import com.benki.weather.network.models.NetworkResponse
import com.benki.weather.network.models.WeatherApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) :
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
}