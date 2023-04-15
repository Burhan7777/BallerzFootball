package com.pzbproduction.ballerzfootball.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.io.InputStream

interface ApiInterface {

    @GET("fixtures")
    suspend fun getTodaysMatches(@QueryMap map: HashMap<String, String>):
            Response<ApiDataClassWrapper<ApiDataClass>>

    @GET
    suspend fun getImagesOfClubs(@Url image: String): Response<ResponseBody>

    @GET("fixtures")
    suspend fun getMatchesBySelectionFromSpinner(@QueryMap map: HashMap<String, String>):
            Response<ApiDataClassWrapper<ApiDataClass>>

    @GET("fixtures/between/{startDate}/{endDate}")
    suspend fun getMatchesBySpecificDate(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
        @QueryMap map: HashMap<String, String>
    ): Response<ApiDataClassWrapper<ApiDataClass>>

    @GET("fixtures/{id}")
    suspend fun getLineUps(@Path("id") matchId: String, @QueryMap map: HashMap<String, String>):
            Response<LineUp>


}