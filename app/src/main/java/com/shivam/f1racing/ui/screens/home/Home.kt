package com.shivam.f1racing.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import com.shivam.f1racing.Utils
import com.shivam.f1racing.ui.data.Schedule
import kotlin.math.abs

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

    val scrollState = rememberScrollState()
    var viewportHeight by remember { mutableIntStateOf(1) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onSizeChanged { viewportHeight = it.height }
            .verticalScroll(scrollState)
            .padding(bottom = innerPadding.calculateBottomPadding()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        ScalableItem(scrollState, viewportHeight) {
            Box {
                AutoPager(innerPadding, detailState)
                GetPro(innerPadding)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        ScalableItem(scrollState, viewportHeight) {
            RaceEvents(
                onNavigateToDetail = { schedule -> onNavigateToDetail(schedule) },
                raceState = raceState
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        ScalableItem(scrollState, viewportHeight) {
            LewisImage()
        }

        Spacer(modifier = Modifier.height(40.dp))

        ScalableItem(scrollState, viewportHeight) {
            RaceEvents(
                onNavigateToDetail = { schedule -> onNavigateToDetail(schedule) },
                raceState = raceState
            )
        }

        Spacer(modifier = Modifier.height(40.dp))



        /*Box {
            AutoPager(innerPadding, detailState)
            GetPro(innerPadding)
        }
        RaceEvents(
            onNavigateToDetail = { schedule -> onNavigateToDetail(schedule) },
            raceState = raceState
        )
        LewisImage()*/
    }
}


@Composable
fun ScalableItem(
    scrollState: ScrollState,
    viewportHeight: Int,
    content: @Composable (() -> Unit)
) {
    var itemTop by remember { mutableFloatStateOf(0f) }
    var itemHeight by remember { mutableFloatStateOf(0f) }

    val viewportCenter = scrollState.value + viewportHeight / 2f
    val itemCenter = itemTop + itemHeight / 2f
    val distance = abs(viewportCenter - itemCenter)
    val norm = (distance / (viewportHeight / 2f)).coerceIn(0f, 1f)

    // 1f → 1.1f
    val targetScale = 1f + 0.1f * (1f - norm)
    val scale by animateFloatAsState(targetValue = targetScale, label = "scale")

    Box(
        modifier = Modifier
            .onGloballyPositioned { coords ->
                itemTop = coords.positionInParent().y
                itemHeight = coords.size.height.toFloat()
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        content()
    }
}


