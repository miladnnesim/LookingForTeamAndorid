package com.ehb.lookingforteam.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ValorantColorScheme = darkColorScheme(
    primary = ValoRed,
    secondary = ValoGrey,
    background = ValoDark,
    surface = ValoGrey,
    onPrimary = ValoWhite,
    onBackground = ValoWhite,
    onSurface = ValoWhite
)

@Composable
fun LookingForTeamTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ValorantColorScheme,
        typography = Typography,
        content = content
    )
}