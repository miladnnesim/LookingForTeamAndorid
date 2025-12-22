package com.ehb.lookingforteam.model

data class PlayerProfile(
    val name: String = "",
    val rank: String = "",
    val region: String = "",
    val role: String = "", // Duelist, Controller, etc.
    val language: String = "",
    val availability: String = "",
    val playstyle: String = "" // Casual of Competitive
)