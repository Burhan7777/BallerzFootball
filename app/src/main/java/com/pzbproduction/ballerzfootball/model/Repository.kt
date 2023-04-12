package com.pzbproduction.ballerzfootball.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

class Repository {

    private var retrofitInstance = ApiInstance.getInstance()
    private var apiInterface = retrofitInstance?.create(ApiInterface::class.java)
    private var apiDataClass: ArrayList<ApiDataClass>? = ArrayList()
    private var logosOfHomeClubs: ArrayList<Bitmap>? = ArrayList()
    private var logosOfAwayClubs: ArrayList<Bitmap>? = ArrayList()
    private var logosOfHomeClubsFromSpecificDate: ArrayList<Bitmap>? = ArrayList()
    private var logosOfAwayClubsFromSpecificDate: ArrayList<Bitmap>? = ArrayList()
    private var apiDataClassFromSpinner: ArrayList<ApiDataClass>? = ArrayList()
    private var matchBySpecificDate: ArrayList<ApiDataClass>? = ArrayList()
    private var isLoading = false
    private var hasLoaded: Boolean = false

    fun getTodaysMatch(map: HashMap<String, String>): ArrayList<ApiDataClass>? {
        //  Log.i("sizeViewModel", Thread.currentThread().name.toString())
        isLoading = true
        var apiCall = apiInterface?.getTodaysMatches(map)
        var response = apiCall?.execute()
        if (response!!.isSuccessful) {
            var apiDataClassWrapper = response.body()
            apiDataClass = apiDataClassWrapper?.data?.toCollection(ArrayList())
            if (apiDataClass?.size != null) {
                getLogosOfHomeClubs()
                getLogosOfAwayClubs()
            } else {
                hasLoaded = true
            }
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
        hasLoaded = true
        // Log.i("sizeImages", homeImagesOfClubs?.size.toString())
        return logosOfAwayClubs
    }

    fun getIsLoading(): Boolean = isLoading
    fun getHasLoaded(): Boolean = hasLoaded

    fun getMatchesBySelectionFromSpinner(map: HashMap<String, String>): ArrayList<ApiDataClass>? {
        var apiCall = apiInterface?.getMatchesBySelectionFromSpinner(map)
        var response = apiCall?.execute()
        if (response!!.isSuccessful) {
            apiDataClassFromSpinner = response.body()?.data?.toCollection(ArrayList())
        }
        return apiDataClassFromSpinner
    }

    fun getMatchBySpecificDate(
        startDate: String,
        endDate: String,
        map: HashMap<String, String>
    ): ArrayList<ApiDataClass>? {
        var apiCall = apiInterface?.getMatchesBySpecificDate(startDate, endDate, map)
        var response = apiCall?.execute()
        if (response!!.isSuccessful) {
            matchBySpecificDate = response.body()?.data?.toCollection(ArrayList())
            if (matchBySpecificDate?.size != null) {
                getLogosOfAwayClubsFromSpecificDate()
                getLogosOfHomeClubsFromSpecificDate()
            }
        }
        return matchBySpecificDate
    }

    fun getLogosOfHomeClubsFromSpecificDate(): ArrayList<Bitmap>? {
        for (i in matchBySpecificDate!!.indices) {
            if (matchBySpecificDate!![i].participants[0].meta.location == "home") {
                var pathString = matchBySpecificDate!![i].participants[0].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfHomeClubsFromSpecificDate?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            } else if (matchBySpecificDate!![i].participants[1].meta.location == "home") {
                var pathString = matchBySpecificDate!![i].participants[1].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfHomeClubsFromSpecificDate?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            }
        }
        Log.i("sizeImages", logosOfHomeClubsFromSpecificDate?.size.toString())
        return logosOfHomeClubsFromSpecificDate
    }

    fun getLogosOfAwayClubsFromSpecificDate(): ArrayList<Bitmap>? {
        for (i in matchBySpecificDate!!.indices) {
            if (matchBySpecificDate!![i].participants[0].meta.location == "away") {
                var pathString = matchBySpecificDate!![i].participants[0].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfAwayClubsFromSpecificDate?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            } else if (matchBySpecificDate!![i].participants[1].meta.location == "away") {
                var pathString = matchBySpecificDate!![i].participants[1].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                var response = apiCall?.execute()
                if (response!!.isSuccessful) {
                    logosOfAwayClubsFromSpecificDate?.add(
                        BitmapFactory.decodeStream(
                            response.body()?.byteStream()!!
                        )
                    )
                }
            }
        }
        hasLoaded = true
        Log.i("sizeImages", logosOfAwayClubsFromSpecificDate?.size.toString())
        return logosOfAwayClubsFromSpecificDate
    }
}