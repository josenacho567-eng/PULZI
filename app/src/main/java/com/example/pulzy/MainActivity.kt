package com.example.pulzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pulzy.ui.*
import com.example.pulzy.ui.theme.PulzyTheme
import kotlinx.coroutines.delay

@Preview(name = "Pulzi App", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PulziAppPreview() {
    PulzyTheme {
        PulziApp(showSplash = false)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PulzyTheme { PulziApp() } }
    }
}

@Composable
fun PulziApp(showSplash: Boolean = true) {
    var loading by rememberSaveable { mutableStateOf(showSplash) }
    var selected by rememberSaveable { mutableStateOf("Inicio") }
    LaunchedEffect(Unit) {
        if (loading) {
            delay(1400)
            loading = false
        }
    }
    BackHandler(enabled = selected != "Inicio") { selected = "Inicio" }
    if (loading) {
        SplashScreen()
    } else {
        Scaffold(bottomBar = {
            NavigationBar {
                listOf("Inicio" to "⌂", "Eventos" to "☆", "Perfil" to "☺").forEach { (title, symbol) ->
                    NavigationBarItem(
                        selected = selected == title,
                        onClick = { selected = title },
                        icon = { Text(symbol) },
                        label = { Text(title) }
                    )
                }
            }
        }) { padding ->
            when (selected) {
                "Eventos" -> EventsScreen(Modifier.padding(padding))
                "Perfil" -> ProfileScreen(Modifier.padding(padding))
                else -> HomeScreen(Modifier.padding(padding), onEvents = { selected = "Eventos" })
            }
        }
    }
}
