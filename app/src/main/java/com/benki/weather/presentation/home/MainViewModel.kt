package com.benki.weather.presentation.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benki.weather.data.repository.WeatherRepository
import com.benki.weather.data.repository.WeatherWorkerRepository
import com.benki.weather.network.models.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

data class UiState(val query: String = "", val searchActive: Boolean = false)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val workerRepository: WeatherWorkerRepository
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    val networkResponse = weatherRepository.networkResponse.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        NetworkResponse.Idle
    )

    init {
        workerRepository.enqueuePeriodicWorkRequest(Duration.ofHours(6L))
        viewModelScope.launch {
            val location = weatherRepository.getDeviceLastLocation()
            if (location != null) {
                weatherRepository.getWeatherUpdates(
                    query = "${location.latitude},${location.longitude}",
                    "yes"
                )
            } else {
                weatherRepository.getWeatherUpdates(query = "bengaluru", "yes")
            }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun toggleSearch(searchActive: Boolean) {
        _uiState.update {
            it.copy(searchActive = searchActive)
        }
    }

    fun search(query: String) {
        if (query.isNotEmpty()) {
            _uiState.update {
                it.copy(searchActive = false)
            }
            viewModelScope.launch {
                weatherRepository.getWeatherUpdates(query, "yes")
            }
        } else {
            viewModelScope.launch {
                val location: Location? = weatherRepository.getDeviceLastLocation()
                location?.let {
                    weatherRepository.getWeatherUpdates("${it.latitude},${it.longitude}", "yes")
                }
            }
        }
    }
}