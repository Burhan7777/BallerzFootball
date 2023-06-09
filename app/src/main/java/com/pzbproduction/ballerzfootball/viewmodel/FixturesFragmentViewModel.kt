package com.pzbproduction.ballerzfootball.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import com.pzbproduction.ballerzfootball.model.FixtureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FixturesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var apiDataClass: MutableLiveData<ArrayList<ApiDataClass>> = MutableLiveData()
    private var logosOfHomeClubs: MutableLiveData<ArrayList<Bitmap>> = MutableLiveData()
    private var logosOfAwayClubs: MutableLiveData<ArrayList<Bitmap>> = MutableLiveData()
    private var logosOfHomeClubsFromSpecificDate: MutableLiveData<ArrayList<Bitmap>> =
        MutableLiveData()
    private var logosOfAwayClubsFromSpecificDate: MutableLiveData<ArrayList<Bitmap>> =
        MutableLiveData()
    private var apiDataClassFromSpinner: MutableLiveData<ArrayList<ApiDataClass>> =
        MutableLiveData()
    private var matchesBySpecificDate: MutableLiveData<ArrayList<ApiDataClass>> =
        MutableLiveData()
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var hasLoaded: MutableLiveData<Boolean> = MutableLiveData()
    private var repository = FixtureRepository(application)

    private var job1: Job? = null

    val getApiDataClass: LiveData<ArrayList<ApiDataClass>> get() = apiDataClass
    val getLogosOfHomeClubs: LiveData<ArrayList<Bitmap>> get() = logosOfHomeClubs
    val getLogosOfAwayClubs: LiveData<ArrayList<Bitmap>> get() = logosOfAwayClubs
    val getLogosOfHomeClubsFromSpecificDate: LiveData<ArrayList<Bitmap>> get() = logosOfHomeClubsFromSpecificDate
    val getLogosOfAwayClubsFromSpecificDate: LiveData<ArrayList<Bitmap>> get() = logosOfAwayClubsFromSpecificDate
    val getApiDataClassFromSpinner: LiveData<ArrayList<ApiDataClass>> get() = apiDataClassFromSpinner
    val getMatchesBySpecificDate: LiveData<ArrayList<ApiDataClass>> get() = matchesBySpecificDate

    val getIsLoading: LiveData<Boolean> get() = isLoading
    val getHasLoaded: LiveData<Boolean> get() = hasLoaded

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

    fun getLogosOfHomeClubFromSpecificDate() {
        viewModelScope.launch(Dispatchers.IO) {
            logosOfHomeClubsFromSpecificDate.postValue(repository.logosOfHomeClubsFromSpecificDate)
        }
    }

    fun getLogosOfAwayClubFromSpecificDate() {
        viewModelScope.launch(Dispatchers.IO) {
            logosOfAwayClubsFromSpecificDate.postValue(repository.logosOfAwayClubsFromSpecificDate)
        }
    }


    fun getApiDataClassFromSpinner(map: HashMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            apiDataClassFromSpinner.postValue(repository.getMatchesBySelectionFromSpinner(map))
        }
    }

    fun getMatchesBySpecificDate(startDate: String, endDate: String, map: HashMap<String, String>) {
        job1 = viewModelScope.launch(Dispatchers.IO) {
            matchesBySpecificDate.postValue(
                repository.getMatchBySpecificDate(
                    startDate,
                    endDate,
                    map
                )
            )
        }
//        Log.i("random", matchesBySpecificDate.value.toString())
    }


    fun getIsLoading() {
        isLoading.postValue(repository.getIsLoading())

    }

    fun getHasLoaded() {
        hasLoaded.postValue(repository.getHasLoaded())
    }

    fun jobCancelled() {
        job1?.cancel()
    }


}