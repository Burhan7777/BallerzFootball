package com.pzbproduction.ballerzfootball.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.io.InputStream

interface ApiInterface {

    @GET("fixtures")
    fun getTodaysMatches(@QueryMap map: HashMap<String, String>):
            Call<ApiDataClassWrapper<ApiDataClass>>

    @GET
    fun getImagesOfClubs(@Url image: String): Call<ResponseBody>
}