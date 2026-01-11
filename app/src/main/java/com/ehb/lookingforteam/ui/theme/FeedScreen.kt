package com.ehb.lookingforteam.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ehb.lookingforteam.model.LftPost
import com.ehb.lookingforteam.viewmodel.FeedViewModel
import com.ehb.lookingforteam.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(feedViewModel: FeedViewModel, profileViewModel: ProfileViewModel) {
    val posts by feedViewModel.posts.collectAsState()
    val activeRank by feedViewModel.rankFilter.collectAsState()
    val activeRole by feedViewModel.roleFilter.collectAsState()
    val myProfile by profileViewModel.profile.collectAsState()

    val ranks = listOf("Radiant", "Immortal", "Ascendant", "Diamond", "Platinum", "Gold")
    val roles = listOf("Duelist", "Controller", "Initiator", "Sentinel")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "LIVE FEED",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = ValoRed,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Filter op Rank:", style = MaterialTheme.typography.labelMedium, color = ValoWhite)
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(vertical = 4.dp)) {
            FilterChip(
                selected = activeRank == null,
                onClick = { feedViewModel.setRankFilter(null) },
                label = { Text("Alle") }
            )
            ranks.forEach { rank ->
                Spacer(modifier = Modifier.width(4.dp))
                FilterChip(
                    selected = activeRank == rank,
                    onClick = { feedViewModel.setRankFilter(rank) },
                    label = { Text(rank) }
                )
            }
        }

        Text("Filter op Role:", style = MaterialTheme.typography.labelMedium, color = ValoWhite)
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(vertical = 4.dp)) {
            FilterChip(
                selected = activeRole == null,
                onClick = { feedViewModel.setRoleFilter(null) },
                label = { Text("Alle") }
            )
            roles.forEach { role ->
                Spacer(modifier = Modifier.width(4.dp))
                FilterChip(
                    selected = activeRole == role,
                    onClick = { feedViewModel.setRoleFilter(role) },
                    label = { Text(role) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(posts) { post ->
                val isMyPost = post.playerName == myProfile.name && myProfile.name.isNotEmpty()
                LftCard(post, isMyPost) { feedViewModel.deletePost(post.id) }
            }
        }
    }
}

@Composable
fun LftCard(post: LftPost, isMyPost: Boolean, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        // Eigen posts krijgen een rode rand in plaats van alleen een donkere kleur
        border = if (isMyPost) BorderStroke(2.dp, ValoRed) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isMyPost) Color(0xFF1A252E) else ValoGrey,
            contentColor = ValoWhite
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = post.playerName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isMyPost) ValoRed else ValoWhite
                    )
                    Text(
                        text = "${post.role} • ${post.region}",
                        style = MaterialTheme.typography.bodySmall,
                        color = ValoWhite.copy(alpha = 0.7f)
                    )
                }
                Surface(
                    color = ValoRed,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = post.rank,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = ValoWhite.copy(alpha = 0.2f))

            Text(
                text = post.message,
                style = MaterialTheme.typography.bodyMedium,
                color = ValoWhite
            )

            if (isMyPost) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(
                        onClick = onDelete,
                        colors = ButtonDefaults.textButtonColors(contentColor = ValoRed)
                    ) {
                        Text("VERWIJDER MIJN POST", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}