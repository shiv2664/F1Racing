package com.shivam.f1racing.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun HomeScreen(innerPadding: PaddingValues = PaddingValues(0.dp)) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Box {
            AutoPager(innerPadding)
            GetPro(innerPadding)
        }
        RaceEvents()
        LewisImage()



        /* val driver = Driver(
               driverId = "max-verstappen",
               podiums = 11,
               points = 321,
               poles = 7,
               position = 3,
               teamId = "red-bull",
               wins = 5,
               firstName = "Max",
               lastName = "Verstappen",
               driverCode = "VER",
               teamName = "Red Bull",
               racingNumber = 1
           )*/
        /*Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 1.dp, 0.dp, 1.dp)
        ) {
            *//*            when (val state = detailState) {
                            is NetworkResult.Loading -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }

                            is NetworkResult.Success -> {
                                state.data?.let { movie ->
                                    MovieDetailsContent(movie = movie)
                                } ?: run {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text("Movie details not found.")
                                    }
                                }
                            }

                            is NetworkResult.Error -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("Error: ${state.message ?: "Unknown error"}")
                                }
                            }
                        }*//*
        }*/
    }
}

