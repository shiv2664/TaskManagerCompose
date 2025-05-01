package com.shivam.taskmanagercompose.ui.screens.createlisting

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
fun CreateListingScreen(
    onNavigateBack: () -> Unit,
    onListingCreated: (Long) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Create Listing Screen", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onListingCreated(202) }) {
            Text("Create and View Listing 202")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateBack) {
            Text("Cancel")
        }
    }
}