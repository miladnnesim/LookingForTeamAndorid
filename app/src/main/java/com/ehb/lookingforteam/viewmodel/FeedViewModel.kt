package com.ehb.lookingforteam.viewmodel

import androidx.lifecycle.ViewModel
import com.ehb.lookingforteam.model.LftPost
import com.ehb.lookingforteam.model.PlayerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeedViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<LftPost>>(emptyList())
    val posts: StateFlow<List<LftPost>> = _posts.asStateFlow()

    init {
        // Voorbeeld data voor de feed
        _posts.value = listOf(
            LftPost(1, "TenZ#NA1", "Radiant", "Duelist", "NA", "Looking for a serious scrim team."),
            LftPost(2, "ScreaM#ONE", "Immortal 3", "Duelist", "EUW", "One taps only.")
        )
    }

    // De functie accepteert nu een customMessage
    fun addPostFromProfile(profile: PlayerProfile, customMessage: String) {
        val newPost = LftPost(
            id = System.currentTimeMillis().toInt(),
            playerName = profile.name,
            rank = profile.rank,
            role = profile.role,
            region = profile.region,
            // Als het bericht leeg is, gebruiken we een standaardtekst
            message = if (customMessage.isBlank()) "Looking for team!" else customMessage
        )
        // Voeg de nieuwe post toe aan de bovenkant van de lijst
        _posts.update { currentPosts -> listOf(newPost) + currentPosts }
    }

    fun deletePost(postId: Int) {
        _posts.update { currentPosts -> currentPosts.filter { it.id != postId } }
    }
}