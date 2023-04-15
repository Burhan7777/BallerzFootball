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


        if (list.lineups[position].formationField != " ") {
            holder.lineUpJerseyNumber.text = list.lineups[position].jerseyNumber
            holder.lineUpPlayerName.text = list.lineups[position].playerName
        }
    }

    override fun getItemCount(): Int {
        return size
    }
}