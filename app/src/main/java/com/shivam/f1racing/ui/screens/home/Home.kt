package com.shivam.f1racing.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import com.shivam.f1racing.Utils
import com.shivam.f1racing.ui.data.Schedule

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onNavigateToDetail: (Schedule?) -> Unit
) {
    val context= LocalContext.current



    val viewModel: HomeViewModel = hiltViewModel()
    val detailState by viewModel.driverDetailsResponse.collectAsState(null)
    val raceState by viewModel.raceDetailsResponse.collectAsState(null)

    LaunchedEffect(Unit) {
        if (Utils.hasInternetConnection(context)) {
            viewModel.getDriverDetails("https://mocki.io/v1/e8616da8-220c-4aab-a670-ab2d43224ecb")
            viewModel.getRaceDetails("https://mocki.io/v1/9086a3f1-f02b-4d24-8dd3-b63582f45e67")
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(bottom = innerPadding.calculateBottomPadding()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Box {
            AutoPager(innerPadding, detailState)
            GetPro(innerPadding)
        }
        RaceEvents(
            onNavigateToDetail = { schedule -> onNavigateToDetail(schedule) },
            raceState = raceState
        )
        LewisImage()
    }
}

