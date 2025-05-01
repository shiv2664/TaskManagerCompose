package com.shivam.taskmanagercompose.ui.screens.chatlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ChatListScreen(
    onNavigateBack: () -> Unit,
    onChatClick: (Long) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Chat List", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onChatClick(5) }) {
            Text("Chat with User 5")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}