package com.ehb.lookingforteam.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    var isEditing by remember { mutableStateOf(false) }

    // State voor de velden
    var name by remember { mutableStateOf("") }
    var selectedRank by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }
    var selectedRegion by remember { mutableStateOf("") }

    // Opties voor de dropdowns
    val ranks = listOf("Iron", "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Ascendant", "Immortal", "Radiant")
    val roles = listOf("Duelist", "Sentinel", "Initiator", "Controller")
    val regions = listOf("EUW", "NA", "BR", "AP", "KR", "LATAM")

    // Validatie voor de hashtag
    val isNameValid = name.contains("#") && name.split("#").lastOrNull()?.isNotEmpty() == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isEditing) {
            Button(onClick = { isEditing = true }) {
                Text("Maak profiel aan")
            }
        } else {
            Text(text = "Vul je Valorant Profiel in", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            // Username met Hashtag verplichting
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Gebruikersnaam (bijv. User#1234)") },
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
                onClick = { isEditing = false },
                modifier = Modifier.fillMaxWidth(),
                enabled = isNameValid && selectedRank.isNotEmpty() && selectedRole.isNotEmpty() && selectedRegion.isNotEmpty()
            ) {
                Text("Profiel Opslaan")
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
            readOnly = true, // Zorgt dat je niet zelf kunt typen
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
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
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}