package com.pzbproduction.ballerzfootball.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(context: Context) {

    private var context: Context

    init {
        this.context = context
    }

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
    private var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

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
        matchBySpecificDate?.clear()
        if (logosOfHomeClubsFromSpecificDate?.size!! > 0 || logosOfAwayClubsFromSpecificDate?.size!! > 0) {
            logosOfHomeClubsFromSpecificDate?.clear()
            logosOfAwayClubsFromSpecificDate?.clear()
        }
        var apiCall = apiInterface?.getMatchesBySpecificDate(startDate, endDate, map)
        var response = apiCall?.execute()
        if (response!!.isSuccessful) {
            matchBySpecificDate = response.body()?.data?.toCollection(ArrayList())
            //   if (matchBySpecificDate?.size!! > 0) {
            getLogosOfAwayClubsFromSpecificDate()
            getLogosOfHomeClubsFromSpecificDate()
            Log.i("sizeAction", "Hello")
            Log.i("sizeThread", Thread.currentThread().name.toString())
            //   }
        }
        Log.i("size", matchBySpecificDate?.size.toString())
        return matchBySpecificDate
    }

    fun getLogosOfHomeClubsFromSpecificDate(): ArrayList<Bitmap>? {
        try {
            if (matchBySpecificDate?.isNotEmpty()!!) {
                for (i in 0 until matchBySpecificDate?.size!!) {
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
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            coroutineScope.launch {
                Toast.makeText(context, "Calm down.. Too many requests", Toast.LENGTH_SHORT).show()
            }
        }
        //  Log.i("sizeImages", logosOfHomeClubsFromSpecificDate?.size.toString())
        return logosOfHomeClubsFromSpecificDate
    }

    fun getLogosOfAwayClubsFromSpecificDate(): ArrayList<Bitmap>? {
        /* if (logosOfAwayClubsFromSpecificDate?.size!! != 0) {
             logosOfAwayClubsFromSpecificDate?.clear()
         }*/
        try {
            if (matchBySpecificDate?.isNotEmpty()!!) {
                for (i in 0 until matchBySpecificDate?.size!!) {
                    Log.i("sizeIndices", matchBySpecificDate?.indices.toString())
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
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            coroutineScope.launch {
                Toast.makeText(context, "Chill..Too many requests", Toast.LENGTH_SHORT).show()
            }
        }
        hasLoaded = true
        //  Log.i("sizeImages", logosOfAwayClubsFromSpecificDate?.size.toString())
        return logosOfAwayClubsFromSpecificDate
    }
}