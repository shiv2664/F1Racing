package com.shivam.f1racing.ui.data

data class DriverDetails(
    val drivers: List<Driver>
)
data class Driver(
    val driverCode: String,
    val driverId: String,
    val firstName: String,
    val lastName: String,
    val podiums: Int,
    val points: Int,
    val poles: Int,
    val position: Int,
    val racingNumber: Int,
    val teamId: String,
    val teamName: String,
    val wins: Int
)