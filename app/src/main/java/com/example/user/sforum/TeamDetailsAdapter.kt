package com.example.user.sforum

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.team_details_row.view.*


class TeamDetailsAdapter(val players : PlayersData) : RecyclerView.Adapter<TeamViewHolder>() {

    override fun getItemCount(): Int {
        return players.players.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TeamViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_details_row, parent, false)

        return TeamViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TeamViewHolder?, position: Int) {

        val player = players.players.get(position)
        holder?.view?.number_text?.text = player.jerseyNumber
        holder?.view?.name_text?.text = player.name
        holder?.view?.position_text?.text = player.position
    }

}

class TeamViewHolder(val view : View) : RecyclerView.ViewHolder(view)
