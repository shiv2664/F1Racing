package com.shivam.f1racing.ui.screens.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.f1racing.data.NetworkResult
import com.shivam.f1racing.data.RacingRepository
import com.shivam.f1racing.ui.data.DriverDetails
import com.shivam.f1racing.ui.data.RaceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: RacingRepository, val application: Application): ViewModel() {
    private val _driverDetailsResponse =
        MutableSharedFlow<NetworkResult<DriverDetails>?>()
    val driverDetailsResponse: SharedFlow<NetworkResult<DriverDetails>?> =
        _driverDetailsResponse

    private val _raceDetailsResponse =
        MutableSharedFlow<NetworkResult<RaceDetails>?>()
    val raceDetailsResponse: SharedFlow<NetworkResult<RaceDetails>?> =
        _raceDetailsResponse


    fun getDriverDetails(url: String){
        viewModelScope.launch {
            repository.getDriverData(url).collect { result ->

                _driverDetailsResponse.emit(result)

            }
        }

    }

    fun getRaceDetails(url: String){
        viewModelScope.launch {
            repository.getRaceDetails(url).collect { result ->

                _raceDetailsResponse.emit(result)

            }
        }

    }


}