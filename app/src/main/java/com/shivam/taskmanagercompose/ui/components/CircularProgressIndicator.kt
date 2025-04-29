package com.shivam.taskmanagercompose.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TaskProgressIndicator(
    completedTasks: Int,
    totalTasks: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    animationDuration: Int = 1000,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    var progressValue by remember { mutableStateOf(0f) }
    val percentage = if (totalTasks > 0) (completedTasks.toFloat() / totalTasks.toFloat()) else 0f
    
    val animatedPercentage by animateFloatAsState(
        targetValue = percentage,
        label = "Progress Animation"
    )

    LaunchedEffect(percentage) {
        progressValue = percentage
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        ) {
            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // Foreground circle
            val sweepAngle = animatedPercentage * 360f
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${(animatedPercentage * 100).toInt()}%",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$completedTasks/$totalTasks",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 