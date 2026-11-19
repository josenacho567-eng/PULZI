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
import com.example.pulzy.model.Usuario
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

    var usuario by remember { mutableStateOf(Usuario()) }
    val userLikedTopics = usuario.temas
    val savedEventIds = usuario.eventosGuardados
    var eventsInitialFilter by rememberSaveable {
        mutableStateOf("Todos")
    }

    val onToggleSave: (String) -> Unit = { id ->
        val nuevos = if (savedEventIds.contains(id)) {
            savedEventIds - id
        } else {
            savedEventIds + id
        }
        usuario = usuario.copy(eventosGuardados = nuevos)
    }

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
                    onUserEntered = { nombre ->
                        usuario = Usuario(nombre = nombre.ifBlank { "Estudiante PULZI" })
                    },
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
                    onRegistrationData = { nombre, universidad, temas ->
                        usuario = Usuario(
                            nombre = nombre.ifBlank { "Estudiante PULZI" },
                            universidad = universidad.ifBlank { "Universidad Mayor de San Andrés" },
                            temas = temas
                        )
                    },
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
                                    onClick = {
                                        if (title == "Eventos" && selectedTab != "Eventos") {
                                            eventsInitialFilter = "Todos"
                                        }
                                        selectedTab = title
                                    },
                                    icon = {
                                        if (title == "Eventos" && savedEventIds.isNotEmpty()) {
                                            BadgedBox(
                                                badge = {
                                                    Badge { Text("${savedEventIds.size}") }
                                                }
                                            ) {
                                                Text(symbol)
                                            }
                                        } else {
                                            Text(symbol)
                                        }
                                    },
                                    label = { Text(title) }
                                )
                            }
                        }
                    }
                ) { padding ->
                    when (selectedTab) {
                        "Eventos" -> EventsScreen(
                            savedEventIds = savedEventIds,
                            onToggleSave = onToggleSave,
                            initialFilter = eventsInitialFilter,
                            modifier = Modifier.padding(padding)
                        )
                        "Perfil" -> ProfileScreen(
                            usuario = usuario,
                            onUpdateLikedTopics = { usuario = usuario.copy(temas = it) },
                            onPhotoSelected = { usuario = usuario.copy(fotoUri = it) },
                            onNavigateToSavedEvents = {
                                eventsInitialFilter = "★ Guardados"
                                selectedTab = "Eventos"
                            },
                            onLogout = {
                                usuario = Usuario()
                                currentDestination = PulziDestination.Login
                            },
                            modifier = Modifier.padding(padding)
                        )
                        else -> HomeScreen(
                            userLikedTopics = userLikedTopics,
                            savedEventIds = savedEventIds,
                            onToggleSave = onToggleSave,
                            onNavigateToEvents = { topicFilter ->
                                eventsInitialFilter = topicFilter ?: "Todos"
                                selectedTab = "Eventos"
                            },
                            modifier = Modifier.padding(padding)
                        )
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
