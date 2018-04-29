package com.example.user.sforum.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import com.example.user.sforum.FixturesData
import com.example.user.sforum.FixturesFragmentAdapter
import com.example.user.sforum.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_fixtures.*
import okhttp3.*
import org.jetbrains.anko.find
import java.io.IOException


class FixturesFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(id : String) : FixturesFragment {
            val args = Bundle()
            args.putString("id", id)
            val fragment = FixturesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var rView : RecyclerView? = null
    val client = OkHttpClient()
    var round = 2
    var nOfTeams = 20

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_fixtures, container, false)
        val btn1 : Button = view.find(R.id.previousRound_button)
        val btn2 : Button = view.find(R.id.nextRound_button)
        rView = view.findViewById(R.id.recyclerView_fixtures) as RecyclerView?
        rView?.setHasFixedSize(true)
        rView?.layoutManager = LinearLayoutManager(context)

        fetchJson()

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView_fixtures.setHasFixedSize(true)
        recyclerView_fixtures.layoutManager = LinearLayoutManager(context)
    }

    override fun onClick(v: View?) {
        val leagueId = arguments.getString("id")
        when (v?.id) {
            R.id.previousRound_button -> {
                round--
                Json(round, nOfTeams, leagueId)
            }
            R.id.nextRound_button -> {
                round++
                Json(round, nOfTeams, leagueId)
            }
        }
    }

    fun fetchJson() {
        val leagueId = arguments.getString("id")

        val url = "http://api.football-data.org/v1/competitions/" + leagueId

        val request = Request.Builder().header("X-Auth-Token", "f1bb718b9d994e4a81b78ffd2b6806d4").url(url).build()

        client.newCall(request).enqueue(object  : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                println("It's OK.")
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val leagueday = gson.fromJson(body, CurrentMatchday::class.java)

                round = leagueday.currentMatchday
                nOfTeams = leagueday.numberOfTeams
                    Json(round, nOfTeams, leagueId)

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request failed.")
            }
        })
    }

    fun Json(round: Int, numberOfTeams: Int, leagueId : String) {
        val url = "http://api.football-data.org/v1/competitions/" + leagueId + "/fixtures"

        val request = Request.Builder().header("X-Auth-Token", "f1bb718b9d994e4a81b78ffd2b6806d4").url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                println("It's OK.")
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val fixtures = gson.fromJson(body, FixturesData::class.java )

                    activity?.runOnUiThread {
                        rView?.adapter = FixturesFragmentAdapter(fixtures, round, numberOfTeams)
                        recyclerView_fixtures.adapter = FixturesFragmentAdapter(fixtures, round, numberOfTeams)
                    }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request failed.")
            }
        })

    }

    class CurrentMatchday(val currentMatchday : Int, val numberOfTeams : Int)

}
