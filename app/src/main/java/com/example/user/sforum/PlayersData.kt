package com.example.user.sforum

import java.io.Serializable


class PlayersData (val players : List<Players>) : Serializable

class Players (val name : String, val position : String, val jerseyNumber : String, dateOfBirth : String, val nationality : String)
