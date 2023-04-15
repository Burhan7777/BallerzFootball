package com.pzbproduction.ballerzfootball.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import com.pzbproduction.ballerzfootball.model.Data
import com.pzbproduction.ballerzfootball.model.MatchDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchDetailsActivityViewModel : ViewModel() {

    private val matchDetailRepository = MatchDetailRepository()

    private var apiDataClass = MutableLiveData<Data>()

    val getApiDataClass: LiveData<Data> get() = apiDataClass

    fun getLineUps(matchId: String, map: HashMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            apiDataClass.postValue(matchDetailRepository.getLineUps(matchId, map))
        }
    }
}