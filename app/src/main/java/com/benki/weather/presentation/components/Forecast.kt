package com.benki.weather.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benki.weather.network.models.ForecastDayItem
import com.benki.weather.utils.DateUtils.convertDateToDay

@Composable
fun Forecast(modifier: Modifier = Modifier, forecastData: List<ForecastDayItem> = emptyList()) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            forecastData.forEachIndexed { index, forecastDayItem ->
                if (index == 0) {
                    ForecastItem(
                        imageUrl = "https:${forecastDayItem.day.condition.icon}",
                        day = convertDateToDay(forecastDayItem.date),
                        temperature = forecastDayItem.day.avgTemperature,
                        modifier = modifier,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                } else {
                    ForecastItem(
                        imageUrl = "https:${forecastDayItem.day.condition.icon}",
                        day = convertDateToDay(forecastDayItem.date),
                        temperature = forecastDayItem.day.avgTemperature,
                        modifier = modifier,
                    )
                }
            }
        }
    }
}