package com.example.pulzy.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulzy.model.DemoEvents
import com.example.pulzy.ui.theme.*

@Composable
fun ProfileScreen(
    userLikedTopics: Set<String>,
    onUpdateLikedTopics: (Set<String>) -> Unit,
    savedEventIds: Set<String>,
    onNavigateToSavedEvents: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Page(modifier = modifier) {
        Heading(
            title = "Tu espacio PULZI",
            subtitle = "Gestiona tus preferencias universitarias y eventos guardados."
        )

        // Tarjeta Perfil de Usuario
        Card(
            colors = CardDefaults.cardColors(containerColor = Violet),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Surface(
                        color = Yellow,
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "☺",
                            fontSize = 32.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Estudiante PULZI",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "La Paz, Bolivia · Universidad Mayor de San Andrés",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Tarjeta Resumen de Eventos Guardados
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Ink.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "★ Mis eventos guardados",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Ink
                    )
                    Text(
                        text = "Tienes ${savedEventIds.size} plan(es) guardado(s)",
                        fontSize = 13.sp,
                        color = Ink.copy(alpha = 0.7f)
                    )
                }

                Button(
                    onClick = onNavigateToSavedEvents,
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow, contentColor = Ink),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Ver guardados", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // Selección Interactiva de Temas Favoritos
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Ink.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Tus temas de interés",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Ink
                    )
                    Text(
                        text = "Selecciona tus temas preferidos para personalizar tu pantalla de Inicio:",
                        fontSize = 13.sp,
                        color = Ink.copy(alpha = 0.7f)
                    )
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DemoEvents.topics.forEach { topic ->
                        val isSelected = userLikedTopics.contains(topic)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                val updated = if (isSelected) {
                                    userLikedTopics - topic
                                } else {
                                    userLikedTopics + topic
                                }
                                onUpdateLikedTopics(updated)
                            },
                            label = {
                                Text(
                                    text = topic,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
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
        }

        Spacer(Modifier.height(8.dp))

        // Botón de Cerrar Sesión
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Violet),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, Violet),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Cerrar sesión (Volver al ingreso)", fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = Ink.copy(alpha = 0.1f))

        Text(
            text = "PULZI ✦ La Paz, Bolivia\nPrimer avance - Entrevista y prototipo navegable.",
            color = Violet,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}

@Preview(name = "ProfileScreen Preview", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ProfileScreenPreview() {
    PulzyTheme {
        ProfileScreen(
            userLikedTopics = setOf("Música", "Cultura", "Comunidad"),
            onUpdateLikedTopics = {},
            savedEventIds = setOf("evt_1"),
            onNavigateToSavedEvents = {},
            onLogout = {}
        )
    }
}
