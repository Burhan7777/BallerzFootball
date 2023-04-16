package com.pzbproduction.ballerzfootball.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixtureRepository(context: Context) {

    private var context: Context

    init {
        this.context = context
    }

    private var retrofitInstance = ApiInstance.getInstance()
    private var apiInterface = retrofitInstance?.create(ApiInterface::class.java)
    private var apiDataClass: ArrayList<ApiDataClass>? = ArrayList()
    private var logosOfHomeClubs: ArrayList<Bitmap>? = ArrayList()
    private var logosOfAwayClubs: ArrayList<Bitmap>? = ArrayList()
    var logosOfHomeClubsFromSpecificDate: ArrayList<Bitmap>? = ArrayList()
    var logosOfAwayClubsFromSpecificDate: ArrayList<Bitmap>? = ArrayList()
    private var apiDataClassFromSpinner: ArrayList<ApiDataClass>? = ArrayList()
    private var matchBySpecificDate: ArrayList<ApiDataClass>? = ArrayList()
    private var isLoading = false
    private var hasLoaded: Boolean = false
    private var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun getTodaysMatch(map: HashMap<String, String>): ArrayList<ApiDataClass>? {
        isLoading = true
        var apiCall = apiInterface?.getTodaysMatches(map)
        if (apiCall!!.isSuccessful) {
            var apiDataClassWrapper = apiCall.body()
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

    // Each "data" array returns two objects of "participants" and these two objects
    // randomly refer to the home team and the away team. Hence here we check for
    // both "participants" as anyone can contain home and away participant details.

    suspend fun getLogosOfHomeClubs(): ArrayList<Bitmap>? {
        for (i in apiDataClass!!.indices) {
            if (apiDataClass!![i].participants[0].meta.location == "home") {
                var pathString = apiDataClass!![i].participants[0].image
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                if (apiCall!!.isSuccessful) {
                    logosOfHomeClubs?.add(
                        BitmapFactory.decodeStream(
                            apiCall.body()?.byteStream()!!
                        )
                    )
                }
            } else if (apiDataClass!![i].participants[1].meta.location == "home") {
                var pathString = apiDataClass!![i].participants[1].image
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                if (apiCall!!.isSuccessful) {
                    logosOfHomeClubs?.add(
                        BitmapFactory.decodeStream(
                            apiCall.body()?.byteStream()!!
                        )
                    )
                }
            }
        }
        // Log.i("sizeImages", homeImagesOfClubs?.size.toString())
        return logosOfHomeClubs
    }

    // Similarly here we search for the away team logos in both objects of "participants"

    suspend fun getLogosOfAwayClubs(): ArrayList<Bitmap>? {
        for (i in apiDataClass!!.indices) {
            if (apiDataClass!![i].participants[0].meta.location == "away") {
                var pathString = apiDataClass!![i].participants[0].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                if (apiCall!!.isSuccessful) {
                    logosOfAwayClubs?.add(
                        BitmapFactory.decodeStream(
                            apiCall.body()?.byteStream()!!
                        )
                    )
                }
            } else if (apiDataClass!![i].participants[1].meta.location == "away") {
                var pathString = apiDataClass!![i].participants[1].image
                //  Log.i("sizePath", pathString)
                var apiCall = apiInterface?.getImagesOfClubs(pathString)
                if (apiCall!!.isSuccessful) {
                    logosOfAwayClubs?.add(
                        BitmapFactory.decodeStream(
                            apiCall.body()?.byteStream()!!
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

    suspend fun getMatchesBySelectionFromSpinner(map: HashMap<String, String>): ArrayList<ApiDataClass>? {
        var apiCall = apiInterface?.getMatchesBySelectionFromSpinner(map)
        if (apiCall!!.isSuccessful) {
            apiDataClassFromSpinner = apiCall.body()?.data?.toCollection(ArrayList())
        }
        return apiDataClassFromSpinner
    }

    suspend fun getMatchBySpecificDate(
        startDate: String,
        endDate: String,
        map: HashMap<String, String>
    ): ArrayList<ApiDataClass>? {

        matchBySpecificDate = ArrayList()
        matchBySpecificDate?.clear()


        logosOfAwayClubsFromSpecificDate?.clear()
        logosOfHomeClubsFromSpecificDate?.clear()


        Log.i("sizeArray", matchBySpecificDate.toString())

        var apiCall = apiInterface?.getMatchesBySpecificDate(startDate, endDate, map)

        if (apiCall!!.isSuccessful) {
            matchBySpecificDate = apiCall.body()?.data?.toCollection(ArrayList())

        }

        if (matchBySpecificDate != null) {
            getLogosOfAwayClubsFromSpecificDate()
            getLogosOfHomeClubsFromSpecificDate()
        }

        //  Log.i("size", matchBySpecificDate?.size.toString())


        return matchBySpecificDate
    }

    suspend fun getLogosOfHomeClubsFromSpecificDate(): ArrayList<Bitmap>? {
        try {
            for (i in matchBySpecificDate!!.indices) {
                if (matchBySpecificDate!![i].participants[0].meta.location == "home") {
                    var pathString = matchBySpecificDate!![i].participants[0].image
                    var apiCall = apiInterface?.getImagesOfClubs(pathString)
                    if (apiCall!!.isSuccessful) {
                        logosOfHomeClubsFromSpecificDate?.add(
                            BitmapFactory.decodeStream(
                                apiCall.body()?.byteStream()!!
                            )
                        )
                    }
                } else if (matchBySpecificDate!![i].participants[1].meta.location == "home") {
                    var pathString = matchBySpecificDate!![i].participants[1].image
                    var apiCall = apiInterface?.getImagesOfClubs(pathString)
                    if (apiCall!!.isSuccessful) {
                        logosOfHomeClubsFromSpecificDate?.add(
                            BitmapFactory.decodeStream(
                                apiCall.body()?.byteStream()!!
                            )
                        )
                    }
                }
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            coroutineScope.launch {
                Toast.makeText(context, "Chill2.. Too many requests", Toast.LENGTH_SHORT).show()
            }
        }
        Log.i("sizeImagesHome", logosOfHomeClubsFromSpecificDate?.size.toString())
        return logosOfHomeClubsFromSpecificDate
    }

    suspend fun getLogosOfAwayClubsFromSpecificDate(): ArrayList<Bitmap>? {
        try {
            for (i in matchBySpecificDate!!.indices) {
                Log.i("sizeIndices", matchBySpecificDate?.indices.toString())
                if (matchBySpecificDate!![i].participants[0].meta.location == "away") {
                    var pathString = matchBySpecificDate!![i].participants[0].image
                    var apiCall = apiInterface?.getImagesOfClubs(pathString)

                    if (apiCall!!.isSuccessful) {
                        logosOfAwayClubsFromSpecificDate?.add(
                            BitmapFactory.decodeStream(
                                apiCall.body()?.byteStream()!!
                            )
                        )
                    }
                } else if (matchBySpecificDate!![i].participants[1].meta.location == "away") {
                    var pathString = matchBySpecificDate!![i].participants[1].image
                    var apiCall = apiInterface?.getImagesOfClubs(pathString)
                    if (apiCall!!.isSuccessful) {
                        logosOfAwayClubsFromSpecificDate?.add(
                            BitmapFactory.decodeStream(
                                apiCall.body()?.byteStream()!!
                            )
                        )
                    }
                }
            }
        } catch (e: java.lang.IndexOutOfBoundsException) {
            coroutineScope.launch {
                Toast.makeText(context, "Chill1..Too many requests", Toast.LENGTH_SHORT).show()

            }
        }
        hasLoaded = true
        Log.i("sizeImagesAway", logosOfAwayClubsFromSpecificDate?.size.toString())
        return logosOfAwayClubsFromSpecificDate
    }
}
