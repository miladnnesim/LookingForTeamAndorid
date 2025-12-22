package com.ehb.lookingforteam.ui.theme



import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp



@Composable

fun ProfileScreen() {

    var isEditing by remember { mutableStateOf(false) }



    var name by remember { mutableStateOf("") }

    var rank by remember { mutableStateOf("") }

    var role by remember { mutableStateOf("") }

    var region by remember { mutableStateOf("") }



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

// De invoervelden verschijnen "daarna"

            Text(text = "Vul je Valorant Profiel in", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))



            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Gebruikersnaam") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))



            OutlinedTextField(value = rank, onValueChange = { rank = it }, label = { Text("Rank (bijv. Gold 2)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))



            OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Main Role (bijv. Duelist)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))



            OutlinedTextField(value = region, onValueChange = { region = it }, label = { Text("Regio (bijv. EUW)") }, modifier = Modifier.fillMaxWidth())



            Spacer(modifier = Modifier.height(24.dp))



            Button(

                onClick = {

                    isEditing = false

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Profiel Opslaan")

            }

        }

    }

}