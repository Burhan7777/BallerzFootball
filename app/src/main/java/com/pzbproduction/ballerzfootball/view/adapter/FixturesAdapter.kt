package com.pzbproduction.ballerzfootball.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pzbproduction.ballerzfootball.R
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class FixturesAdapter(context: Context) :
    RecyclerView.Adapter<FixturesAdapter.ViewHolder>() {

    private var context: Context
    private var list: List<ApiDataClass>? = ArrayList()
    private var logosOfHomeClubs: List<Bitmap> = ArrayList()
    private var logosOfAwayClubs: List<Bitmap> = ArrayList()

    init {
        this.context = context
    }

    fun updateList(list: List<ApiDataClass>?) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getLogosOfHomeClubs(logosOfHomeClubs: List<Bitmap>) {
        this.logosOfHomeClubs = logosOfHomeClubs
    }

    fun getLogosOfAwayClubs(logosOfAwayClubs: List<Bitmap>) {
        this.logosOfAwayClubs = logosOfAwayClubs
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fixtureTeamHomeImage: ImageView = view.findViewById(R.id.fixtureHomeTeamImage)
        val fixtureTeamAwayImage: ImageView = view.findViewById(R.id.fixtureAwayTeamImage)
        val fixtureHomeTeamName: TextView = view.findViewById(R.id.fixtureHomeTeamName)
        val fixtureAwayTeamName: TextView = view.findViewById(R.id.fixtureAwayTeamName)
        val fixtureTime: TextView = view.findViewById(R.id.fixtureTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_fixture_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.fixtureTime.text = changeTheDateFormatOfReceivedTime(list!![position].time)

        if (list!![position].participants[0].meta.location == "home")
            holder.fixtureHomeTeamName.text = list!![position].participants[0].name
        else if (list!![position].participants[0].meta.location == "away")
            holder.fixtureAwayTeamName.text = list!![position].participants[0].name

        if (list!![position].participants[1].meta.location == "away")
            holder.fixtureAwayTeamName.text = list!![position].participants[1].name
        else if (list!![position].participants[1].meta.location == "home")
            holder.fixtureHomeTeamName.text = list!![position].participants[1].name

        Glide.with(context).load(logosOfHomeClubs[position]).centerInside()
            .into(holder.fixtureTeamHomeImage)

        Glide.with(context).load(logosOfAwayClubs[position]).centerInside()
            .into(holder.fixtureTeamAwayImage)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun changeTheDateFormatOfReceivedTime(time: String): String {
        var originalDataFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
        var date = originalDataFormat.parse(time)
        var targetDataFormat = SimpleDateFormat("HH:mm")
        var finalTime = targetDataFormat.format(date)
        return finalTime
    }
}