package com.example.user.sforum.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.sforum.LeagueTableData

import com.example.user.sforum.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_league_table.*
import okhttp3.*
import java.io.IOException


class LeagueTableFragment : Fragment() {

    companion object {
        fun newInstance(id : String) : LeagueTableFragment {
            val args = Bundle()
            args.putString("id", id)
            val fragment = LeagueTableFragment()
            fragment.arguments = args
            return fragment
        }
    }

        var rView : RecyclerView? = null
        val client = OkHttpClient()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_league_table, container, false)
        rView = view.findViewById(R.id.recyclerView_table) as RecyclerView?
        rView?.setHasFixedSize(true)
        rView?.layoutManager = LinearLayoutManager(context)
        fetchJson()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView_table.setHasFixedSize(true)
        recyclerView_table.layoutManager = LinearLayoutManager(context)
    }


    fun fetchJson() {
        val leagueId = arguments.getString("id")
        val url = "http://api.football-data.org/v1/competitions/" + leagueId +"/leagueTable"

        val request = Request.Builder().header("X-Auth-Token", "f1bb718b9d994e4a81b78ffd2b6806d4").url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                println("It's OK.")
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val leagueTable = gson.fromJson(body, LeagueTableData::class.java )

                    activity?.runOnUiThread {
                        rView?.adapter = LTFragmentAdapter(leagueTable, leagueId)
                        recyclerView_table.adapter = LTFragmentAdapter(leagueTable, leagueId)
                    }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                Toast.makeText(context, "Request failed!", Toast.LENGTH_LONG).show()
            }
        })
    }
}
