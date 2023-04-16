package com.pzbproduction.ballerzfootball.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pzbproduction.ballerzfootball.R
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import com.pzbproduction.ballerzfootball.model.Data

class LineUpAdapter : RecyclerView.Adapter<LineUpAdapter.ViewHolder>() {


    private lateinit var list: Data
    private var size = 0

    // size has been created as recyclerview is not populated when activity
    // is launched but few seconds later when livedata updates. Hence value of size changes
    // in "getTheLineUp" method to the size of the array


    fun getTheLineUp(list: Data) {
        this.list = list
        size = list.lineups.size
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lineUpJerseyNumber: TextView = view.findViewById(R.id.lineUpJerseyNumber)
        var lineUpPlayerName: TextView = view.findViewById(R.id.lineUpPlayerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_lineup_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // "data" object has multiple lineup object where each object points to a player and
        // his / her details. Players on the bench have "formation_field" equal to null and hence
        // here we check of the "formation_field" is not null and hence display the lineup. We can
        // get benched players by equating "formation_field" to null

        if (list.lineups[position].formationField != " ") {
            holder.lineUpJerseyNumber.text = list.lineups[position].jerseyNumber
            holder.lineUpPlayerName.text = list.lineups[position].playerName
        }
    }

    override fun getItemCount(): Int {
        return size
    }
}