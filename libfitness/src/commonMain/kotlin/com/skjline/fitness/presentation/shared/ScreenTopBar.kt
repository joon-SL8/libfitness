package com.skjline.fitness.presentation.shared

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    modifier: Modifier = Modifier,
    label: String,
    action: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                label,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                action()
            }) {
//                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        }
    )
}