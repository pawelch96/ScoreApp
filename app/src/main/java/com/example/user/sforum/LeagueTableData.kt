package com.example.user.sforum

import java.io.Serializable


class LeagueTableData (val leagueCaption : String, val matchday : Int, val standing : List<Standing>) : Serializable

class Standing (val position : String, val teamName : String, val crestURI : String, val playedGames : String, val points : String, val goals : String, val goalsAgainst : String, val goalDifference : String,
                val wins : String, val draws : String, val losses : String)
