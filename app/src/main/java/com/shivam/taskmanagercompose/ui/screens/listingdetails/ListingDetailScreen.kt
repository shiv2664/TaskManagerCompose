package com.shivam.taskmanagercompose.ui.screens.listingdetails

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
fun ListingDetailsScreen(
    listingId: Long,
    onNavigateBack: () -> Unit,
    onCreatorProfileClick: (Long) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Listing Details for $listingId", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onCreatorProfileClick(3) }) {
            Text("View Creator's Profile")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}