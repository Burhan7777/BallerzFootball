package com.pzbproduction.ballerzfootball.model

import com.google.gson.annotations.SerializedName

data class ApiDataClassWrapper<T> constructor(
    val data: List<T>
)

data class LineUp constructor(
    val data: Data
)


data class ApiDataClass constructor(
    val id: String,
    @SerializedName("starting_at")
    val time: String,
    var participants: List<Participants>,
    var scores: List<Scores>,
    var lineups: List<LineUps>
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

data class LineUps constructor(
    @SerializedName("jersey_number")
    val jerseyNumber: String,
    @SerializedName("formation_field")
    val formationField: String,
    @SerializedName("formation_position")
    val formationPosition: String,
    @SerializedName("player_name")
    val playerName: String
)

class Data constructor(
    val id: String,
  //  @SerializedName("starting_at")
   // val time: String,
   // var participants: List<Participants>,
   // var scores: List<Scores>,
    var lineups: List<LineUps>
)

