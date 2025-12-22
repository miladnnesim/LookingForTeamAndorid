package com.ehb.lookingforteam.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ehb.lookingforteam.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    // Haal de huidige opgeslagen status op uit de kluis (ViewModel)
    val savedProfile by profileViewModel.profile.collectAsState()

    // Bepaal of we in de 'bewerk' modus zitten.
    // Als de naam leeg is, beginnen we standaard in de bewerk-modus.
    var isEditing by remember { mutableStateOf(savedProfile.name.isEmpty()) }

    // Tijdelijke state voor de invoervelden (gebaseerd op wat al opgeslagen was)
    var name by remember { mutableStateOf(savedProfile.name) }
    var selectedRank by remember { mutableStateOf(savedProfile.rank) }
    var selectedRole by remember { mutableStateOf(savedProfile.role) }
    var selectedRegion by remember { mutableStateOf(savedProfile.region) }

    // Opties voor de dropdowns
    val ranks = listOf("Iron", "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Ascendant", "Immortal", "Radiant")
    val roles = listOf("Duelist", "Sentinel", "Initiator", "Controller")
    val regions = listOf("EUW", "NA", "BR", "AP", "KR", "LATAM")

    // Validatie: Naam moet een # bevatten en daarna niet leeg zijn
    val isNameValid = name.contains("#") && name.split("#").lastOrNull()?.isNotEmpty() == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        if (!isEditing) {
            // --- WEERGAVE MODUS: Toon het huidige profiel ---
            Text(text = "Jouw Valorant Profiel", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Naam: ${savedProfile.name}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Rank: ${savedProfile.rank}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Role: ${savedProfile.role}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Regio: ${savedProfile.region}", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { isEditing = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Profiel Bewerken")
            }

        } else {
            // --- BEWERK MODUS: Het formulier ---
            Text(text = "Stel je Profiel in", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            // Username veld
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Gebruikersnaam (User#1234)") },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isNotEmpty() && !isNameValid,
                supportingText = {
                    if (name.isNotEmpty() && !isNameValid) {
                        Text("Vergeet je #ID niet!")
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rank Dropdown
            CustomDropdownMenu(label = "Kies je Rank", options = ranks, selectedOption = selectedRank) {
                selectedRank = it
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Role Dropdown
            CustomDropdownMenu(label = "Kies je Role", options = roles, selectedOption = selectedRole) {
                selectedRole = it
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Region Dropdown
            CustomDropdownMenu(label = "Kies je Regio", options = regions, selectedOption = selectedRegion) {
                selectedRegion = it
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Sla de data op in de ViewModel
                    profileViewModel.saveProfile(name, selectedRank, selectedRole, selectedRegion)
                    isEditing = false
                },
                modifier = Modifier.fillMaxWidth(),
                // Knop werkt pas als alles correct is ingevuld
                enabled = isNameValid && selectedRank.isNotEmpty() && selectedRole.isNotEmpty() && selectedRegion.isNotEmpty()
            ) {
                Text("Profiel Opslaan")
            }

            if (savedProfile.name.isNotEmpty()) {
                TextButton(onClick = { isEditing = false }) {
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
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
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
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}