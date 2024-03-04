package com.benki.weather.di

import com.benki.weather.data.repository.WeatherRepository
import com.benki.weather.data.repository.WeatherRepositoryImpl
import com.benki.weather.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi) : WeatherRepository{
        return WeatherRepositoryImpl(weatherApi = weatherApi)
    }
}