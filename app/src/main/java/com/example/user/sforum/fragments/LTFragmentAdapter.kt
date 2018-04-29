package com.example.user.sforum.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ahmadrosid.svgloader.SvgLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.user.sforum.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.league_table_row.view.*
import org.jetbrains.anko.backgroundColor


class LTFragmentAdapter(val leagueTable : LeagueTableData, leagueId : String): RecyclerView.Adapter<CustomViewHolder>() {

    val id = leagueId
    override fun getItemCount(): Int {
        return leagueTable.standing.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.league_table_row, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {

        if (position == 0) {
            holder?.view?.position_text?.text = ""
            holder?.view?.name_text?.text =  "Name"
            holder?.view?.points_text?.text = "Pts"
            holder?.view?.match_text?.text = "MP"
            holder?.view?.goals_text?.text = "G"
        }

        else {
            val standsList : Standing= leagueTable.standing.get(position-1)

            holder?.view?.position_text?.text = standsList.position + "."
            holder?.view?.name_text?.text = standsList.teamName
            holder?.view?.points_text?.text = standsList.points
            holder?.view?.match_text?.text = standsList.playedGames
            holder?.view?.goals_text?.text = standsList.goals + ":" + standsList.goalsAgainst
            holder?.standing = standsList
            holder?.lId = id
            }
        }
    }

class CustomViewHolder(val view : View, var standing : Standing? = null): RecyclerView.ViewHolder(view) {

    var lId : String? = null

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, TeamDetailsActivity::class.java)
            intent.putExtra("teamname", standing?.teamName)
            intent.putExtra("id", lId)
            if (lId!=null) {
                view.context.startActivity(intent)
            }
        }
    }
}











