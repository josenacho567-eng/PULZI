package com.example.pulzy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulzy.ui.theme.*

@androidx.compose.ui.tooling.preview.Preview(
    name = "PULZI · Carga",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SplashScreenPreview() {
    PulzyTheme {
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    Column(
        Modifier.fillMaxSize().background(Cream).safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✳", color = Violet, fontSize = 72.sp)
        Text("PULZI", color = Violet, fontSize = 56.sp, fontWeight = FontWeight.Black)
        Text("Tu ciudad. Tu gente. Tu momento.")
        Spacer(Modifier.height(32.dp))
        CircularProgressIndicator(color = Violet)
    }
}
