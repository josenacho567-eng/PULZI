package com.example.pulzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pulzy.ui.*
import com.example.pulzy.ui.theme.PulzyTheme
import kotlinx.coroutines.delay

enum class PulziDestination {
    Login,
    Register,
    Main
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PulzyTheme { PulziApp() } }
    }
}

@Composable
fun PulziApp(
    showSplash: Boolean = true,
    initialDestination: PulziDestination = PulziDestination.Login
) {
    var loading by rememberSaveable { mutableStateOf(showSplash) }
    var currentDestination by rememberSaveable { mutableStateOf(initialDestination) }
    var selectedTab by rememberSaveable { mutableStateOf("Inicio") }

    LaunchedEffect(Unit) {
        if (loading) {
            delay(1400)
            loading = false
        }
    }

    BackHandler(enabled = !loading && currentDestination == PulziDestination.Register) {
        currentDestination = PulziDestination.Login
    }

    BackHandler(enabled = !loading && currentDestination == PulziDestination.Main && selectedTab != "Inicio") {
        selectedTab = "Inicio"
    }

    if (loading) {
        SplashScreen()
    } else {
        when (currentDestination) {
            PulziDestination.Login -> {
                LoginScreen(
                    onLoginSuccess = {
                        selectedTab = "Inicio"
                        currentDestination = PulziDestination.Main
                    },
                    onNavigateToRegister = {
                        currentDestination = PulziDestination.Register
                    }
                )
            }
            PulziDestination.Register -> {
                RegisterScreen(
                    onRegisterSuccess = {
                        selectedTab = "Inicio"
                        currentDestination = PulziDestination.Main
                    },
                    onNavigateToLogin = {
                        currentDestination = PulziDestination.Login
                    }
                )
            }
            PulziDestination.Main -> {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            listOf("Inicio" to "⌂", "Eventos" to "☆", "Perfil" to "☺").forEach { (title, symbol) ->
                                NavigationBarItem(
                                    selected = selectedTab == title,
                                    onClick = { selectedTab = title },
                                    icon = { Text(symbol) },
                                    label = { Text(title) }
                                )
                            }
                        }
                    }
                ) { padding ->
                    when (selectedTab) {
                        "Eventos" -> EventsScreen(Modifier.padding(padding))
                        "Perfil" -> ProfileScreen(
                            modifier = Modifier.padding(padding),
                            onLogout = {
                                currentDestination = PulziDestination.Login
                            }
                        )
                        else -> HomeScreen(Modifier.padding(padding), onEvents = { selectedTab = "Eventos" })
                    }
                }
            }
        }
    }
}

@Preview(name = "Pulzi App - Login", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PulziAppLoginPreview() {
    PulzyTheme {
        PulziApp(showSplash = false, initialDestination = PulziDestination.Login)
    }
}

@Preview(name = "Pulzi App - Home", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PulziAppHomePreview() {
    PulzyTheme {
        PulziApp(showSplash = false, initialDestination = PulziDestination.Main)
    }
}
