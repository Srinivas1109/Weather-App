package com.benki.weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benki.weather.network.models.NetworkResponse
import com.benki.weather.presentation.components.ErrorComponent
import com.benki.weather.presentation.components.LoadingComponent
import com.benki.weather.presentation.components.RequestPermissions
import com.benki.weather.presentation.home.HomeScreen
import com.benki.weather.presentation.home.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val networkResponse by viewModel.networkResponse.collectAsStateWithLifecycle()
    var loading by remember {
        mutableStateOf(false)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FloatingActionButton(
                    onClick = {
                        viewModel.search(uiState.query)
                    },
                    shape = RoundedCornerShape(8.dp),
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh",
                    )
                }

                FloatingActionButton(
                    onClick = viewModel::searchCurrentLocation,
                    shape = RoundedCornerShape(8.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Your location",
                    )
                }
            }
        }, contentWindowInsets = WindowInsets(0.dp)
    ) { contentPadding ->
        Box(
            modifier = modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF232526),
                            Color(0xFF414345)
                        )
                    )
                )
                .padding(8.dp)
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            RequestPermissions()
            Column(modifier = modifier.fillMaxSize()) {
                SearchBar(
                    query = uiState.query,
                    onQueryChange = viewModel::updateQuery,
                    onSearch = {
                        keyboardController?.hide()
                        viewModel.search(query = uiState.query)
                    },
                    active = false,
                    onActiveChange = viewModel::toggleSearch,
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = SearchBarDefaults.colors(
                        containerColor = Color(0xFF414345),
                        dividerColor = Color(0xFF414345),
                        inputFieldColors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF414345),
                            unfocusedContainerColor = Color(0xFF414345),
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            focusedLeadingIconColor = Color.White,
                            unfocusedLeadingIconColor = Color.White,
                            cursorColor = Color.White,
                            focusedPlaceholderColor = Color.White,
                            unfocusedPlaceholderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedTrailingIconColor = Color.White,
                            unfocusedTrailingIconColor = Color.White,
                        )
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Location",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        if (uiState.query.isNotEmpty()) {
                            IconButton(onClick = {
                                viewModel.updateQuery("")
                                focusManager.clearFocus()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(text = "Search here...", color = Color.White)
                    }
                ) {
                }
                when (networkResponse) {
                    is NetworkResponse.Success -> {
                        loading = false
                        HomeScreen(
                            weatherApiResponse = (networkResponse as NetworkResponse.Success).data,
                        )
                    }

                    is NetworkResponse.Error -> {
                        loading = false
                        ErrorComponent(error = (networkResponse as NetworkResponse.Error).data.message)
                    }

                    NetworkResponse.Idle -> loading = false
                    NetworkResponse.Loading -> LoadingComponent(query = uiState.query)
                }
            }
        }
    }
}