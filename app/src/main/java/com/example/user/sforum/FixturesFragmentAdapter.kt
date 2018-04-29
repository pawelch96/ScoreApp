package com.example.user.sforum

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.sforum.fragments.FixturesFragment
import kotlinx.android.synthetic.main.fixtures_row.view.*
import org.jetbrains.anko.custom.style


class FixturesFragmentAdapter(val fixturesData: FixturesData, val round: Int, val numberOfTeams : Int) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return numberOfTeams/2 + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.fixtures_row, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {

        if (position == 0) {
            holder?.view?.homeTeam_text?.text = " Matchday :"
            holder?.view?.awayTeamScore_text?.text = round.toString()

        }

        var iter = (round) * 10 - 10
        for (i in 0..9){
            val fixtureList = fixturesData.fixtures.get(iter + i)
            if (position == i+1) {
                var bufor = fixtureList.date.replace("T", " ", false)
                var buffor = bufor.replace("Z", " ", false)
                var dateText = buffor.replaceRange(16, 19, " ")


                if (fixtureList.result.goalsAwayTeam == 0 && fixtureList.result.goalsHomeTeam == 0 &&
                        (fixtureList.status.equals("TIMED") || fixtureList.status.equals("SCHEDULED") || fixtureList.status.equals("POSTPONED"))){
                    holder?.view?.homeTeamScore_text?.text = "-"
                    holder?.view?.awayTeamScore_text?.text = "-"
                }
                else {
                    holder?.view?.homeTeamScore_text?.text = fixtureList.result.goalsHomeTeam.toString()
                    holder?.view?.awayTeamScore_text?.text = fixtureList.result.goalsAwayTeam.toString()

                    if (fixtureList.status.equals("IN_PLAY")) {
                        holder?.view?.homeTeamScore_text?.setTextColor(Color.parseColor("#008000"))
                        holder?.view?.awayTeamScore_text?.setTextColor(Color.parseColor("#008000"))
                    }
                }

                if (fixtureList.result.goalsHomeTeam > fixtureList.result.goalsAwayTeam) {
                    holder?.view?.homeTeam_text?.setTextColor(Color.parseColor("#008000"))
                    holder?.view?.homeTeam_text?.setTypeface(null, Typeface.BOLD)
                }
                else if (fixtureList.result.goalsHomeTeam < fixtureList.result.goalsAwayTeam) {
                    holder?.view?.awayTeam_text?.setTextColor(Color.parseColor("#008000"))
                    holder?.view?.awayTeam_text?.setTypeface(null, Typeface.BOLD)
                }

                holder?.view?.data_text?.text = dateText
                holder?.view?.homeTeam_text?.text = fixtureList.homeTeamName
                holder?.view?.awayTeam_text?.text = fixtureList.awayTeamName
            }
        }
    }
}

class CustomViewHolder(val view : View): RecyclerView.ViewHolder(view)