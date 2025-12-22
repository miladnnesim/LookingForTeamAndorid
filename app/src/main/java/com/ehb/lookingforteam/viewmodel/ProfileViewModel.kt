package com.ehb.lookingforteam.viewmodel

import androidx.lifecycle.ViewModel
import com.ehb.lookingforteam.model.PlayerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    // Dit is de 'kluis' waar je profiel in zit.
    // We beginnen met een leeg profiel.
    private val _profile = MutableStateFlow(PlayerProfile())
    val profile: StateFlow<PlayerProfile> = _profile.asStateFlow()

    // Deze functie roepen we aan om de data echt 'op te slaan' in het geheugen
    fun saveProfile(newName: String, newRank: String, newRole: String, newRegion: String) {
        _profile.value = PlayerProfile(
            name = newName,
            rank = newRank,
            role = newRole,
            region = newRegion
        )
    }
}