package com.shivam.f1racing.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shivam.f1racing.R

@Composable
@Preview
fun DetailScreen(innerPadding: PaddingValues = PaddingValues(0.dp)) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(bottom = innerPadding.calculateBottomPadding())) {
        UpcomingRaceDetails(innerPadding)

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
            Text(
                text = "São Paulo  Circuit",
                fontFamily = FontFamily(Font(R.font.space_regular)),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Bahrain International circuit is located in Sakhir, Bahrain and it was designed by German architect Hermann Tilke. It was built on the site of a former camel farm, in Sakhir. It measures 5.412 km, has 15 corners and 3 DRS Zones. The Grand Prix have 57 laps. This circuit has 6 alternative layouts.",
                fontFamily = FontFamily(Font(R.font.space_regular)),
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Circuit Facts",
                fontFamily = FontFamily(Font(R.font.space_regular)),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "His brother Arthur Leclerc is currently set to race for DAMS in the 2023 F2 Championship\n\n\n\nHe’s not related to Édouard Leclerc, the founder of a French supermarket chain",
                fontFamily = FontFamily(Font(R.font.space_regular)),
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

        }
    }

}