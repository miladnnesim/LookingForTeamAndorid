package com.ehb.lookingforteam.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ehb.lookingforteam.viewmodel.FeedViewModel
import com.ehb.lookingforteam.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, feedViewModel: FeedViewModel) {
    val savedProfile by profileViewModel.profile.collectAsState()

    // State om te bepalen of we bewerken of bekijken
    var isEditing by remember { mutableStateOf(savedProfile.name.isEmpty()) }

    // State voor het LFT bericht dat je wilt plaatsen
    var postDescription by remember { mutableStateOf("") }

    // Tijdelijke states voor het formulier
    var name by remember { mutableStateOf(savedProfile.name) }
    var selectedRank by remember { mutableStateOf(savedProfile.rank) }
    var selectedRole by remember { mutableStateOf(savedProfile.role) }
    var selectedRegion by remember { mutableStateOf(savedProfile.region) }

    val ranks = listOf("Iron", "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Ascendant", "Immortal", "Radiant")
    val roles = listOf("Duelist", "Sentinel", "Initiator", "Controller")
    val regions = listOf("EUW", "NA", "BR", "AP", "KR", "LATAM")

    val isNameValid = name.contains("#") && name.split("#").lastOrNull()?.isNotEmpty() == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        if (!isEditing) {
            // --- MODUS: PROFIEL BEKIJKEN ---
            Text(text = "Jouw Valorant Profiel", style = MaterialTheme.typography.headlineLarge)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Naam: ${savedProfile.name}", style = MaterialTheme.typography.titleMedium)
                    Text("Rank: ${savedProfile.rank}")
                    Text("Role: ${savedProfile.role}")
                    Text("Regio: ${savedProfile.region}")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Sectie om een post te maken
            Text(text = "Plaats een LFT bericht", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = postDescription,
                onValueChange = { postDescription = it },
                label = { Text("Wat zoek je? (bijv. Zoek duo voor ranked)") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                minLines = 3
            )

            Button(
                onClick = {
                    feedViewModel.addPostFromProfile(savedProfile, postDescription)
                    postDescription = "" // Reset het veld na posten
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = savedProfile.name.isNotEmpty()
            ) {
                Text("Post naar LFT Feed")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = { isEditing = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Profielgegevens Aanpassen")
            }

        } else {
            // --- MODUS: PROFIEL BEWERKEN ---
            Text(text = "Stel je Profiel in", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Gebruikersnaam (User#1234)") },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isNotEmpty() && !isNameValid
            )

            CustomDropdownMenu("Kies je Rank", ranks, selectedRank) { selectedRank = it }
            CustomDropdownMenu("Kies je Role", roles, selectedRole) { selectedRole = it }
            CustomDropdownMenu("Kies je Regio", regions, selectedRegion) { selectedRegion = it }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    profileViewModel.saveProfile(name, selectedRank, selectedRole, selectedRegion)
                    isEditing = false
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isNameValid && selectedRank.isNotEmpty() && selectedRole.isNotEmpty()
            ) {
                Text("Profiel Opslaan")
            }

            if (savedProfile.name.isNotEmpty()) {
                TextButton(onClick = { isEditing = false }, modifier = Modifier.fillMaxWidth()) {
                    Text("Annuleren")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}