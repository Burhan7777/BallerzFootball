package com.pzbproduction.ballerzfootball.model

import com.google.gson.annotations.SerializedName

data class ApiDataClassWrapper<T> constructor(
    val data: List<T>
)


data class ApiDataClass constructor(
    @SerializedName("starting_at")
    val time: String,
    var participants: List<Participants>,
    var scores: List<Scores>
)

data class Participants constructor(
    val name: String,
    val meta: MetaData,
    @SerializedName("image_path")
    val image: String
)

data class MetaData constructor(
    val location: String?
)

data class Scores constructor(
    val description: String,
    val score: Score
)

data class Score constructor(
    var goals: String,
    var participant: String
)