package com.ehb.lookingforteam.model

data class LftPost(
    val id: Int,
    val playerName: String,
    val rank: String,
    val role: String,
    val region: String,
    val message: String
)