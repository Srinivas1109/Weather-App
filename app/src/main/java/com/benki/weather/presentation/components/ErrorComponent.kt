package com.benki.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benki.weather.R

@Composable
fun ErrorComponent(modifier: Modifier = Modifier, error: String) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.align(
                Alignment.Center
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.sad_emoji), contentDescription = null)
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = error, color = Color.White, fontSize = 18.sp,
            )
        }
    }
}