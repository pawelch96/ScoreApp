package com.example.user.sforum

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.user.sforum.fragments.FixturesFragment
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_team_details.*
import kotlinx.android.synthetic.main.league_table_row.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import okhttp3.*
import org.jetbrains.anko.UI
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import java.io.IOException
import java.io.Serializable


class TeamDetailsActivity : AppCompatActivity(), Serializable {

    val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_team_details)
        //rView? = recyclerView_td.findViewById(R.id.recyclerView_td) as RecyclerView?
        recyclerView_td.setHasFixedSize(true)
        recyclerView_td.layoutManager = LinearLayoutManager(this)

        val teamName = intent.getStringExtra("teamname")
        team_name_text.text = teamName


            fetchJson()

            //fetchJson()
            //fillTeamLayout(tData)

    }

     fun fetchJson() {

         val id = intent.getStringExtra("id")
         val url = "http://api.football-data.org/v1/competitions/" + id + "/teams"
         val request = Request.Builder().header("X-Auth-Token", "f1bb718b9d994e4a81b78ffd2b6806d4").url(url).build()

        client.newCall(request).enqueue(object  : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                println("It's OK.")
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                async(UI) {
                val team = bg { gson.fromJson(body, TeamData::class.java) }

                fillPlayersLayout(team.await())}
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request failed.")
            }
        })

    }

    suspend fun fillPlayersLayout(team : TeamData) {

        val teamName = intent.getStringExtra("teamname")
            for (i in 0..team.teams.size-1) {
                if (team.teams[i].name == teamName) {
                   // team_name_text.text = teamName
                    //tData = team.teams[i]
                    if (team.teams[i].crestUrl.contains("svg")) {
                        async(UI) {
                            SvgLoader.pluck().with(this@TeamDetailsActivity).load(team.teams[i].crestUrl, team_logo)
                        }
                        }
                    else {
                        async(UI) {
                            Glide.with(applicationContext).load(team.teams[i].crestUrl).into(team_logo)
                        }
//                       val myBitmap : Bitmap = Glide.with(applicationContext).load(team.teams[i].crestUrl).asBitmap().centerCrop().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
//                        team_logo.setImageBitmap(myBitmap)
                    }
                    val url = team.teams[i]._links.players.href
                    getPlayersData(url)


                }
            }


    }

   suspend fun getPlayersData(url : String) {

        val request = Request.Builder().header("X-Auth-Token", "f1bb718b9d994e4a81b78ffd2b6806d4").url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson =  GsonBuilder().create()
                val players = gson.fromJson(body, PlayersData::class.java)

                runOnUiThread {
                    recyclerView_td.adapter = TeamDetailsAdapter(players)
                }

//                    runOnUiThread {
//                        async(UI) {
//                            val players = bg { gson.fromJson(body, PlayersData::class.java) }
//
//                        recyclerView_td.adapter = TeamDetailsAdapter(players.await())
//                    }
//                }


            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request failed.")
            }
        })

    }
//
//    fun fillTeamLayout(tData : Team) {
//
//                            if (tData.crestUrl.contains("svg")) {
//                        SvgLoader.pluck().with(this@TeamDetailsActivity).load(tData.crestUrl, team_logo)
//                    }
//                    else {
//                        Glide.with(applicationContext).load(tData.crestUrl).into(team_logo)
////                       val myBitmap : Bitmap = Glide.with(applicationContext).load(team.teams[i].crestUrl).asBitmap().centerCrop().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
////                        team_logo.setImageBitmap(myBitmap)
//                    }

   // }
}