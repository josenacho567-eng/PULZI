package com.example.pulzy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Cream = Color(0xFFFFF8EA)
val Violet = Color(0xFF6633CC)
val Yellow = Color(0xFFFFD84D)
val Ink = Color(0xFF292039)

@Composable
fun PulzyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Violet, onPrimary = Color.White,
            secondary = Yellow, onSecondary = Ink,
            background = Cream, surface = Cream,
            onBackground = Ink, onSurface = Ink
        ),
        typography = Typography,
        content = content
    )
}
