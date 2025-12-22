package com.ehb.lookingforteam.viewmodel

import androidx.lifecycle.ViewModel
import com.ehb.lookingforteam.model.LftPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<LftPost>>(emptyList())
    val posts: StateFlow<List<LftPost>> = _posts.asStateFlow()

    init {
        // We vullen de lijst direct met wat voorbeeld data
        _posts.value = listOf(
            LftPost(1, "TenZ#NA1", "Radiant", "Duelist", "NA", "Looking for a serious scrim team. I play Jett/Chamber."),
            LftPost(2, "ScreaM#ONE", "Immortal 3", "Duelist", "EUW", "Only one taps. Looking for chill competitive teammates."),
            LftPost(3, "Boaster#FNC", "Ascendant", "Controller", "EUW", "Need a tactical team for VCT Open Qualifiers."),
            LftPost(4, "Player123#BEL", "Gold 2", "Sentinel", "EUW", "Casual games tonight? Just want to have fun.")
        )
    }
}