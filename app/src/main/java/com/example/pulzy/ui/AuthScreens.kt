package com.example.pulzy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulzy.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onUserEntered: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    initialNombre: String = "matias.estudiante",
    initialPassword: String = "pulzi2026"
) {
    var nombre by rememberSaveable { mutableStateOf(initialNombre) }
    var password by rememberSaveable { mutableStateOf(initialPassword) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        // Cabecera institucional PULZI
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "PULZI  ✦",
                color = Violet,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "LA PAZ, BOLIVIA  •  A TU RITMO",
                color = Violet,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }

        Spacer(Modifier.height(4.dp))

        // Títulos de la pantalla
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Inicio de sesión",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Ink
            )
            Text(
                text = "Bienvenido de vuelta. Conecta con eventos, grupos y la vida universitaria paceña.",
                style = MaterialTheme.typography.bodyLarge,
                color = Ink.copy(alpha = 0.75f)
            )
        }

        // Tarjeta con formulario
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PulziField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre o usuario",
                    placeholder = "Ingresa cualquier nombre o usuario"
                )

                PulziField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    placeholder = "Ingresa tu contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(4.dp))

                PulziButton(
                    text = "Entrar  →",
                    onClick = { onUserEntered(nombre); onLoginSuccess() },
                    variant = PulziButtonVariant.Primary
                )
            }
        }

        // Navegación hacia Registro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes una cuenta?",
                color = Ink.copy(alpha = 0.75f),
                fontSize = 15.sp
            )
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "Crear cuenta",
                    color = Violet,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(Modifier.weight(1f, fill = false))

        // Tarjeta informativa inferior
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8DBFF)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("✦", fontSize = 24.sp, color = Violet)
                Text(
                    text = "Hecho para estudiantes de La Paz.\nExplora sin restricciones ni barreras.",
                    fontSize = 13.sp,
                    color = Ink,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegistrationData: (String, String, Set<String>) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier,
    initialNombre: String = "Camila Flores",
    initialPassword: String = "lapaz2026",
    initialUniversidad: String = "UPB",
    initialTemas: Set<String> = setOf("Música", "Cultura", "Comunidad")
) {
    var nombre by rememberSaveable { mutableStateOf(initialNombre) }
    var password by rememberSaveable { mutableStateOf(initialPassword) }
    var universidad by rememberSaveable { mutableStateOf(initialUniversidad) }

    val availableTemas = listOf(
        "Música",
        "Cultura",
        "Comunidad",
        "Deportes",
        "Tecnología",
        "Arte y Diseño",
        "Vida Nocturna",
        "Estudio y Talleres"
    )

    var selectedTemas by rememberSaveable { mutableStateOf(initialTemas) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Spacer(Modifier.height(4.dp))

        // Cabecera institucional
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "PULZI  ✦",
                color = Violet,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "REGISTRO DE ESTUDIANTE",
                color = Violet,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }

        // Títulos
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "Crear cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Ink
            )
            Text(
                text = "Crea tu perfil universitario para acceder a planes y eventos a tu medida.",
                style = MaterialTheme.typography.bodyLarge,
                color = Ink.copy(alpha = 0.75f)
            )
        }

        // Tarjeta con formulario
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PulziField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre",
                    placeholder = "Tu nombre o apodo universitario"
                )

                PulziField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    placeholder = "Crea tu contraseña",
                    isPassword = true
                )

                PulziField(
                    value = universidad,
                    onValueChange = { universidad = it },
                    label = "Universidad (opcional)",
                    placeholder = "Ej. UMSA, UCB, UNIFRANZ, EMI..."
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Temas",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Ink
                    )
                    Text(
                        text = "Elige los intereses que te mueven:",
                        fontSize = 13.sp,
                        color = Ink.copy(alpha = 0.65f)
                    )

                    FlowRow (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        availableTemas.forEach { tema ->
                            val isSelected = selectedTemas.contains(tema)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedTemas = if (isSelected) {
                                        selectedTemas - tema
                                    } else {
                                        selectedTemas + tema
                                    }
                                },
                                label = {
                                    Text(
                                        text = tema,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 13.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Violet,
                                    selectedLabelColor = Color.White,
                                    containerColor = Cream,
                                    labelColor = Ink
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = if (isSelected) Violet else Ink.copy(alpha = 0.15f),
                                    selectedBorderColor = Violet,
                                    borderWidth = 1.dp
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                PulziButton(
                    text = "Continuar  →",
                    onClick = {
                        onRegistrationData(nombre, universidad, selectedTemas)
                        onRegisterSuccess()
                    },
                    variant = PulziButtonVariant.Primary
                )
            }
        }

        // Navegación hacia Login
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿Ya tienes cuenta?",
                color = Ink.copy(alpha = 0.75f),
                fontSize = 15.sp
            )
            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = "Ya tengo cuenta",
                    color = Violet,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Preview(name = "Inicio de sesión - Texto inventado", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenSamplePreview() {
    PulzyTheme {
        LoginScreen(
            onLoginSuccess = {},
            onNavigateToRegister = {},
            initialNombre = "matias.estudiante",
            initialPassword = "pulzi2026"
        )
    }
}

@Preview(name = "Inicio de sesión - Vacío", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenEmptyPreview() {
    PulzyTheme {
        LoginScreen(
            onLoginSuccess = {},
            onNavigateToRegister = {},
            initialNombre = "",
            initialPassword = ""
        )
    }
}

@Preview(name = "Registro - Texto inventado", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun RegisterScreenSamplePreview() {
    PulzyTheme {
        RegisterScreen(
            onRegisterSuccess = {},
            onNavigateToLogin = {},
            initialNombre = "Camila Flores",
            initialPassword = "lapaz2026",
            initialUniversidad = "UPB",
            initialTemas = setOf("Música", "Cultura", "Comunidad")
        )
    }
}
