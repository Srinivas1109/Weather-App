package com.benki.weather.network.models

sealed class NetworkResponse {
    data class Success(val data: WeatherApiResponse) : NetworkResponse()
    data class Error(val data: ErrorResponse) : NetworkResponse()
    data object Loading : NetworkResponse()
}