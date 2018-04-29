package com.example.user.sforum

import java.io.Serializable


class TeamData (val teams : List<Team>) : Serializable

class Team (val name : String, val _links : Links, val crestUrl : String )

class Links(val players: Player)

class Player (val href : String)