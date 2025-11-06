package com.shivam.f1racing.data

import com.shivam.f1racing.ui.data.DriverDetails
import com.shivam.f1racing.ui.data.RaceDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RacingRepository @Inject constructor(val racingInterface: RacingInterface) {

    fun getDriverData(
        url: String,
    ): Flow<NetworkResult<DriverDetails>> = flow {
        try {
            val response = racingInterface.getDriverList(url)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    emit(NetworkResult.Success(result))
                    return@flow
                }
            }
            emit(
                NetworkResult.Error(
                    response.message() + " Code: " + response.code() + " Error Body: " + response.errorBody().toString()
                )
            )
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    fun getRaceDetails(
        url: String,
    ): Flow<NetworkResult<RaceDetails>> = flow {
        try {
            val response = racingInterface.getRaceList(url)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    emit(NetworkResult.Success(result))
                    return@flow
                }
            }
            emit(
                NetworkResult.Error(
                    response.message() + " Code: " + response.code() + " Error Body: " + response.errorBody().toString()
                )
            )
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

}