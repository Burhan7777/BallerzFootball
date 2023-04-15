package com.pzbproduction.ballerzfootball.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pzbproduction.ballerzfootball.R
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import com.pzbproduction.ballerzfootball.view.activities.MatchDetailActivity
import com.pzbproduction.ballerzfootball.view.fragments.MatchDetailsFragment
import java.text.SimpleDateFormat

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
        notifyDataSetChanged()
    }

    fun getLogosOfAwayClubs(logosOfAwayClubs: List<Bitmap>) {
        this.logosOfAwayClubs = logosOfAwayClubs
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fixtureCardView: CardView = view.findViewById(R.id.fixtureCardView)
        val fixtureTeamHomeImage: ImageView = view.findViewById(R.id.fixtureHomeTeamImage)
        val fixtureTeamAwayImage: ImageView = view.findViewById(R.id.fixtureAwayTeamImage)
        val fixtureHomeTeamName: TextView = view.findViewById(R.id.fixtureHomeTeamName)
        val fixtureAwayTeamName: TextView = view.findViewById(R.id.fixtureAwayTeamName)
        val fixtureHomeScore: TextView = view.findViewById(R.id.fixtureHomeScore)
        val fixtureAwayScore: TextView = view.findViewById(R.id.fixtureAwayScore)
        val fixtureTime: TextView = view.findViewById(R.id.fixtureTime)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_fixture_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if (list?.get(position)?.scores?.size!! > 0) {
            for (i in list!![position].scores.indices) {

                if (list!![position].scores[i].description == "CURRENT") {
                    holder.fixtureAwayScore.visibility = View.VISIBLE
                    holder.fixtureHomeScore.visibility = View.VISIBLE
                    holder.fixtureTime.visibility = View.INVISIBLE

                    if (list!![position].scores[i].score.participant == "home")
                        holder.fixtureHomeScore.text = list!![position].scores[i].score.goals
                    else if (list!![position].scores[i].score.participant == "away")
                        holder.fixtureAwayScore.text = list!![position].scores[i].score.goals

                    if (list!![position].scores[i].score.participant == "home")
                        holder.fixtureHomeScore.text = list!![position].scores[i].score.goals
                    else (list!![position].scores[i].score.participant == "away")
                    holder.fixtureAwayScore.text = list!![position].scores[i].score.goals
                }
            }
        } else {
            holder.fixtureTime.visibility = View.VISIBLE
            holder.fixtureHomeScore.visibility = View.INVISIBLE
            holder.fixtureAwayScore.visibility = View.INVISIBLE
            holder.fixtureTime.text = changeTheDateFormatOfReceivedTime(list!![position].time)

        }

        if (list!![position].participants[0].meta.location == "home")
            holder.fixtureHomeTeamName.text = list!![position].participants[0].name
        else if (list!![position].participants[0].meta.location == "away")
            holder.fixtureAwayTeamName.text = list!![position].participants[0].name

        if (list!![position].participants[1].meta.location == "away")
            holder.fixtureAwayTeamName.text = list!![position].participants[1].name
        else if (list!![position].participants[1].meta.location == "home")
            holder.fixtureHomeTeamName.text = list!![position].participants[1].name

        try {
            Glide.with(context).load(logosOfHomeClubs[position]).centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.fixtureTeamHomeImage)

            Glide.with(context).load(logosOfAwayClubs[position]).centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.fixtureTeamAwayImage)
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Toast.makeText(context, "Chill3...too many requests", Toast.LENGTH_SHORT).show()
        }

        holder.fixtureCardView.setOnClickListener {
           /* var compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Activity(),
                holder.fixtureTeamHomeImage,
                "homeTransition"
            )*/
            val intent = Intent(context, MatchDetailActivity::class.java)
            intent.putExtra("homeLogo", logosOfHomeClubs[position])
            intent.putExtra("awayLogo", logosOfAwayClubs[position])
            intent.putExtra("homeTeam", holder.fixtureHomeTeamName.text)
            intent.putExtra("awayTeam", holder.fixtureAwayTeamName.text)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    private fun changeTheDateFormatOfReceivedTime(time: String): String {
        var originalDataFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
        var date = originalDataFormat.parse(time)
        var targetDataFormat = SimpleDateFormat("HH:mm")
        return targetDataFormat.format(date)
    }
}