package com.pzbproduction.ballerzfootball.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import com.pzbproduction.ballerzfootball.model.Participants
import com.pzbproduction.ballerzfootball.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixturesFragmentViewModel : ViewModel() {

    private var apiDataClass: MutableLiveData<ArrayList<ApiDataClass>> = MutableLiveData()
    private var logosOfHomeClubs: MutableLiveData<ArrayList<Bitmap>> = MutableLiveData()
    private var logosOfAwayClubs: MutableLiveData<ArrayList<Bitmap>> = MutableLiveData()
    private var repository = Repository()

    val getApiDataClass: LiveData<ArrayList<ApiDataClass>> get() = apiDataClass
    val getLogosOfHomeClubs: LiveData<ArrayList<Bitmap>> get() = logosOfHomeClubs
    val getLogosOfAwayClubs: LiveData<ArrayList<Bitmap>> get() = logosOfAwayClubs

    fun getTodaysMatch(map: HashMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            apiDataClass.postValue(repository.getTodaysMatch(map))
            // Log.i("sizeViewModel", getApiDataClass.value.toString())
        }
    }

    fun getLogosOfHomeClub() {
        viewModelScope.launch(Dispatchers.IO) {
            logosOfHomeClubs.postValue(repository.getLogosOfHomeClubs())
        }
    }

    fun getLogosOfAwayClub() {
        viewModelScope.launch(Dispatchers.IO) {
            logosOfAwayClubs.postValue(repository.getLogosOfAwayClubs())
        }
    }


}