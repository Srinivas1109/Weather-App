package com.benki.weather.di

import android.content.Context
import com.benki.weather.data.repository.WeatherRepository
import com.benki.weather.data.repository.WeatherRepositoryImpl
import com.benki.weather.data.repository.WeatherWorkerRepository
import com.benki.weather.data.repository.WeatherWorkerRepositoryImpl
import com.benki.weather.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi, @ApplicationContext context: Context): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi = weatherApi, context = context)
    }

    @Provides
    fun provideWeatherWorkerRepository(@ApplicationContext context: Context):WeatherWorkerRepository{
        return WeatherWorkerRepositoryImpl(context = context)
    }
}