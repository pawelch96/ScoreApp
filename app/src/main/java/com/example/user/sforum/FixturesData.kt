package com.example.user.sforum

import java.io.Serializable
import java.util.*

class FixturesData (val fixtures : List<Fixtures>) : Serializable

class Fixtures ( val date : String, val status : String, val matchday : Int, val homeTeamName : String, val awayTeamName : String, val result : Result)

class Result(val goalsHomeTeam : Int, val goalsAwayTeam : Int)
