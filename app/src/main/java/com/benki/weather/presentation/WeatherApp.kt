package com.benki.weather.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    Scaffold { contentPadding ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)) {

        }
    }
}