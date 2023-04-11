package com.pzbproduction.ballerzfootball.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.InputStream

class Repository {

    private var retrofitInstance = ApiInstance.getInstance()
    private var apiInterface = retrofitInstance?.create(ApiInterface::class.java)
    private var apiDataClass: ArrayList<ApiDataClass>? = ArrayList()
    private var logosOfHomeClubs: ArrayList<Bitmap>? = ArrayList()
    private var logosOfAwayClubs: ArrayList<Bitmap>? = ArrayList()

    fun getTodaysMatch(map: HashMap<String, String>): ArrayList<ApiDataClass>? {
        //  Log.i("sizeViewModel", Thread.currentThread().name.toString())
        var apiCall = apiInterface?.getTodaysMatches(map)
        var response = apiCall?.execute()
        if (response!!.isSuccessful) {
            var apiDataClassWrapper = response.body()
            apiDataClass = apiDataClassWrapper?.data?.toCollection(ArrayList())
            getLogosOfHomeClubs()
            getLogosOfAwayClubs()
          //  Log.i("sizeTodaysMatch", apiDataClass.toString())
        } else {
            Log.i("size", "failed")
        }
        return apiDataClass
    }

    fun getLogosOfHomeClubs(): ArrayList<Bitmap>? {
        for (i in apiDataClass!!.indices) {
            if (apiDataClass!![i].participants[0].meta.location == "home") {
                var pathString = apiDataClass!![i].participants[0].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfHomeClubs?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            } else if (apiDataClass!![i].participants[1].meta.location == "home") {
                var pathString = apiDataClass!![i].participants[1].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfHomeClubs?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            }
        }
        // Log.i("sizeImages", homeImagesOfClubs?.size.toString())
        return logosOfHomeClubs
    }

    fun getLogosOfAwayClubs(): ArrayList<Bitmap>? {
        for (i in apiDataClass!!.indices) {
            if (apiDataClass!![i].participants[0].meta.location == "away") {
                var pathString = apiDataClass!![i].participants[0].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfAwayClubs?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            } else if (apiDataClass!![i].participants[1].meta.location == "away") {
                var pathString = apiDataClass!![i].participants[1].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfAwayClubs?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            }
        }
        // Log.i("sizeImages", homeImagesOfClubs?.size.toString())
        return logosOfAwayClubs
    }
}