package com.pzbproduction.ballerzfootball.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
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
        this.context = context // Context is required to show the toast. You can remove it later
    }

    // LiveData is being used to update the recyclerview and hence nothing goes into
    // constructor of this adapter class.

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

        // There are about 6 objects of "scores" which consists of away goals , home goals,
        // first half goals etc. what we currently need is the final result and hence we check
        // for the "current" which basically shows the final results.

        //  Log.i("score", list?.get(position)?.scores.toString())
        if (list?.get(position)?.scores != null) {
            if (list?.get(position)!!.scores.isNotEmpty()) {
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
        } else {
            holder.fixtureTime.visibility = View.VISIBLE
            holder.fixtureHomeScore.visibility = View.INVISIBLE
            holder.fixtureAwayScore.visibility = View.INVISIBLE
            holder.fixtureTime.text = changeTheDateFormatOfReceivedTime(list!![position].time)

        }

        // Similarly here we have two "participant" objects in each "data" object and hence
        // we get names from either of the "participant" object as they are randomly filled
        // with away and home data details so we check for home and away.

        if (list!![position].participants[0].meta.location == "home")
            holder.fixtureHomeTeamName.text = list!![position].participants[0].name
        else if (list!![position].participants[0].meta.location == "away")
            holder.fixtureAwayTeamName.text = list!![position].participants[0].name

        if (list!![position].participants[1].meta.location == "away")
            holder.fixtureAwayTeamName.text = list!![position].participants[1].name
        else if (list!![position].participants[1].meta.location == "home")
            holder.fixtureHomeTeamName.text = list!![position].participants[1].name

        try {
            if (logosOfAwayClubs.isNotEmpty()  && logosOfHomeClubs.isNotEmpty()) {
                holder.fixtureTeamHomeImage.setImageBitmap(logosOfHomeClubs[position])

                holder.fixtureTeamAwayImage.setImageBitmap(logosOfAwayClubs[position])
            }


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
            intent.putExtra("matchId", list!![position].id)
            intent.putExtra("time", holder.fixtureTime.text.toString())
            intent.putExtra("homeScore", holder.fixtureHomeScore.text.toString())
            intent.putExtra("awayScore", holder.fixtureAwayScore.text.toString())

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        var size = 0
        if (list == null)
            size = 0
        else
            size = list!!.size

        return size
    }

    // this basically changes the date format we get from the API. From API we get time
    // as well as date. We basically change the time-date format into HH:mm

    private fun changeTheDateFormatOfReceivedTime(time: String): String {
        var originalDataFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
        var date = originalDataFormat.parse(time)
        var targetDataFormat = SimpleDateFormat("HH:mm")
        return targetDataFormat.format(date)
    }
}