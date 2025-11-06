package com.shivam.f1racing.data

import com.shivam.f1racing.ui.data.DriverDetails
import com.shivam.f1racing.ui.data.RaceDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RacingInterface {

    @GET
    suspend fun getDriverList(@Url url:String): Response<DriverDetails?>

    @GET
    suspend fun getRaceList(@Url url:String): Response<RaceDetails?>


}