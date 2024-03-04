package com.benki.weather.network.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long = 0L,
    @SerializedName("last_updated")
    val lastUpdated: String = "",
    @SerializedName("temp_c")
    val temperatureCelsius: Double = 0.0,
    @SerializedName("temp_f")
    val temperatureFahrenheit: Double = 0.0,
    @SerializedName("is_day")
    val isDay: Int = 0,
    @SerializedName("condition")
    val weatherCondition: WeatherCondition = WeatherCondition(),
    @SerializedName("wind_mph")
    val windMetersPerHour: Double = 0.0,
    @SerializedName("wind_kph")
    val windKiloMetersPerHour: Double = 0.0,
    @SerializedName("wind_degree")
    val windDegree: Double = 0.0,
    @SerializedName("wind_dir")
    val windDirection: String = "",
    @SerializedName("pressure_mb")
    val pressureMB: Double = 0.0,
    @SerializedName("pressure_in")
    val pressureIN: Double = 0.0,
    @SerializedName("precip_mm")
    val precipMM: Double = 0.0,
    @SerializedName("precip_in")
    val precipIN: Double = 0.0,
    val humidity: Double = 0.0,
    val cloud: Double = 0.0,
    @SerializedName("feelslike_c")
    val feelsLikeCelsius: Double = 0.0,
    @SerializedName("feelslike_f")
    val feelsLikeFahrenheit: Double = 0.0,
    @SerializedName("vis_km")
    val viscosityKilometer: Double = 0.0,
    @SerializedName("vis_miles")
    val viscosityMiles: Double = 0.0,
    val uv: Double = 1.0,
    @SerializedName("gust_mph")
    val gustMPH: Double = 0.0,
    @SerializedName("gust_kph")
    val gustKPH: Double = 0.0,
    @SerializedName("air_quality")
    val airQuality: AirQuality = AirQuality()
)
