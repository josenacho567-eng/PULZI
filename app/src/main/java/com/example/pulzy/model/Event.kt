package com.example.pulzy.model

import androidx.compose.ui.graphics.Color

data class Event(
    val id: String,
    val title: String,
    val topic: String,
    val date: String,
    val location: String,
    val description: String,
    val organizer: String,
    val isFree: Boolean = true,
    val cardBgColor: Color = Color.White
)
