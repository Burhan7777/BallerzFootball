package com.pzbproduction.ballerzfootball.model

import android.util.Log
import retrofit2.Retrofit
import retrofit2.create

class MatchDetailRepository {

    private val retrofitInstance: Retrofit? = ApiInstance.getInstance()
    private val apiInterface: ApiInterface? = retrofitInstance?.create(ApiInterface::class.java)
    private var data: Data? = null

    suspend fun getLineUps(
        matchId: String,
        map: HashMap<String, String>
    ): Data? {
        val apiCall = apiInterface?.getLineUps(matchId, map)

        Log.i("data123",apiCall?.body().toString())
        if (apiCall!!.isSuccessful) {
            data = apiCall.body()?.data
            Log.i("data123", data.toString())
        }
        return data
    }
}