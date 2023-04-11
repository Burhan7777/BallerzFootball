package com.pzbproduction.ballerzfootball.model

import com.google.gson.annotations.SerializedName

data class ApiDataClassWrapper<T> constructor(
    val data: List<T>
)


data class ApiDataClass constructor(
    @SerializedName("starting_at")
    val time: String,
    var participants: List<Participants>
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