package com.pzbproduction.ballerzfootball.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {

    companion object {
        var BASE_URL = "https://api.sportmonks.com/v3/football/"

        private var okHttpClient = OkHttpClient.Builder()
        private var retrofitInstance: Retrofit? = null

        private fun interceptor() {
            var interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClient.addInterceptor(interceptor)

        }

        fun getInstance(): Retrofit? {
            interceptor()
            if (retrofitInstance == null) {
                retrofitInstance = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build()
            }
            return retrofitInstance
        }
    }
}