package com.ehb.lookingforteam.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ehb.lookingforteam.model.LftPost
import com.ehb.lookingforteam.viewmodel.FeedViewModel
import com.ehb.lookingforteam.viewmodel.ProfileViewModel

@Composable
fun FeedScreen(feedViewModel: FeedViewModel, profileViewModel: ProfileViewModel) {
    val posts by feedViewModel.posts.collectAsState()
    val myProfile by profileViewModel.profile.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Live Feed", style = MaterialTheme.typography.headlineLarge)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 16.dp)) {
            items(posts) { post ->
                val isMyPost = post.playerName == myProfile.name && myProfile.name.isNotEmpty()
                LftCard(post, isMyPost) { feedViewModel.deletePost(post.id) }
            }
        }
    }
}

@Composable
fun LftCard(post: LftPost, isMyPost: Boolean, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(post.playerName, style = MaterialTheme.typography.titleMedium)
                Text(post.rank, color = MaterialTheme.colorScheme.secondary)
            }
            Text("${post.role} • ${post.region}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(post.message)
            if (isMyPost) {
                TextButton(onClick = onDelete, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                    Text("Verwijder mijn post")
                }
            }
        }
    }
}