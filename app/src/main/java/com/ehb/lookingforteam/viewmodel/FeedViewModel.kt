package com.ehb.lookingforteam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehb.lookingforteam.model.LftPost
import com.ehb.lookingforteam.model.PlayerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FeedViewModel : ViewModel() {
    // De bron van alle data
    private val _allPosts = MutableStateFlow<List<LftPost>>(emptyList())

    // States voor de actieve filters
    private val _rankFilter = MutableStateFlow<String?>(null)
    private val _roleFilter = MutableStateFlow<String?>(null)

    // De filters die de UI gebruikt om te weten wat er geselecteerd is
    val rankFilter = _rankFilter.asStateFlow()
    val roleFilter = _roleFilter.asStateFlow()

    // De gefilterde lijst. Combine kijkt naar posts, rank en role.
    // Zodra één van die drie verandert, wordt de lijst opnieuw berekend.
    val posts: StateFlow<List<LftPost>> = combine(
        _allPosts,
        _rankFilter,
        _roleFilter
    ) { posts, rank, role ->
        posts.filter { post ->
            (rank == null || post.rank == rank) &&
                    (role == null || post.role == role)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Dummy data bij het opstarten
        _allPosts.value = listOf(
            LftPost(1, "TenZ#NA1", "Radiant", "Duelist", "NA", "Looking for scrims."),
            LftPost(2, "ScreaM#ONE", "Immortal", "Duelist", "EUW", "One taps only."),
            LftPost(3, "Boaster#FNC", "Ascendant", "Controller", "EUW", "Tactical gameplay."),
            LftPost(4, "Chronicle#GMB", "Radiant", "Initiator", "EUW", "Flexible player.")
        )
    }

    // Functies om filters aan te passen
    fun setRankFilter(rank: String?) { _rankFilter.value = rank }
    fun setRoleFilter(role: String?) { _roleFilter.value = role }

    fun addPostFromProfile(profile: PlayerProfile, customMessage: String) {
        val newPost = LftPost(
            id = System.currentTimeMillis().toInt(),
            playerName = profile.name,
            rank = profile.rank,
            role = profile.role,
            region = profile.region,
            message = if (customMessage.isBlank()) "Looking for team!" else customMessage
        )
        _allPosts.update { currentPosts -> listOf(newPost) + currentPosts }
    }

    fun deletePost(postId: Int) {
        _allPosts.update { currentPosts -> currentPosts.filter { it.id != postId } }
    }
}