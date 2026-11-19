package com.example.pulzy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.pulzy.model.DemoEvents
import com.example.pulzy.model.Event
import com.example.pulzy.ui.theme.*

@Composable
fun HomeScreen(
    userLikedTopics: Set<String>,
    savedEventIds: Set<String>,
    onToggleSave: (String) -> Unit,
    onNavigateToEvents: (topicFilter: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedEventForDetail by rememberSaveable { mutableStateOf<Event?>(null) }

    // Filtrar estrictamente solo los eventos de los temas que le gustan al usuario
    val recommendedEvents = remember(userLikedTopics) {
        DemoEvents.list.filter { event -> userLikedTopics.contains(event.topic) }
    }

    Page(modifier = modifier) {
        // Marca e Identidad PULZI
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
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

        // Encabezado
        Heading(
            title = "La vida uni\nse vive afuera.",
            subtitle = "Descubre eventos recomendados según tus gustos e intereses."
        )

        // Banner Hero estilo Figma
        Card(
            colors = CardDefaults.cardColors(containerColor = Violet),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    color = Yellow,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "TU PRÓXIMO GRAN PLAN",
                        color = Ink,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = "Menos rutina.\nMás historias.",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 34.sp
                )
                Text(
                    text = "Encuentros, música y cultura pensados para tu vida universitaria en La Paz.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
                Button(
                    onClick = { onNavigateToEvents(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow, contentColor = Ink),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Explorar la agenda completa  →", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Sección de temas que le gustan al usuario
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Tus temas favoritos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                userLikedTopics.forEach { topic ->
                    FilterChip(
                        selected = true,
                        onClick = { onNavigateToEvents(topic) },
                        label = { Text(topic, fontWeight = FontWeight.SemiBold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Violet,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }
        }

        // Título de la sección de eventos filtrados por intereses
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Para ti en Inicio",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
                Text(
                    text = "Eventos de tus temas de interés (${recommendedEvents.size})",
                    fontSize = 13.sp,
                    color = Ink.copy(alpha = 0.65f)
                )
            }
        }

        // Lista de eventos filtrados por temas del usuario
        if (recommendedEvents.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("✦", fontSize = 32.sp, color = Violet)
                    Text(
                        text = "No hay eventos para tus temas seleccionados.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Ink
                    )
                    Text(
                        text = "Explora todos los temas en la pestaña Eventos o personaliza tus intereses en tu perfil.",
                        fontSize = 14.sp,
                        color = Ink.copy(alpha = 0.7f)
                    )
                    Button(
                        onClick = { onNavigateToEvents(null) },
                        colors = ButtonDefaults.buttonColors(containerColor = Violet)
                    ) {
                        Text("Ir a Eventos")
                    }
                }
            }
        } else {
            recommendedEvents.forEach { event ->
                val isSaved = savedEventIds.contains(event.id)
                EventCard(
                    event = event,
                    isSaved = isSaved,
                    onToggleSave = { onToggleSave(event.id) },
                    onDetailClick = { selectedEventForDetail = event }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Tarjeta pie de página paceña
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE1EED4)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Hecho a 3.600 metros ✦", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink)
                Text(
                    "PULZI es tu guía universitaria en La Paz. Guarda tus eventos preferidos y encuéntralos en la pestaña Eventos.",
                    fontSize = 13.sp,
                    color = Ink.copy(alpha = 0.8f),
                    lineHeight = 18.sp
                )
            }
        }
    }

    // Modal de detalle del evento
    selectedEventForDetail?.let { event ->
        EventDetailDialog(
            event = event,
            isSaved = savedEventIds.contains(event.id),
            onToggleSave = { onToggleSave(event.id) },
            onDismiss = { selectedEventForDetail = null }
        )
    }
}

@Preview(name = "HomeScreen Preview", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenPreview() {
    PulzyTheme {
        HomeScreen(
            userLikedTopics = setOf("Música", "Cultura", "Comunidad"),
            savedEventIds = setOf("evt_1"),
            onToggleSave = {},
            onNavigateToEvents = {}
        )
    }
}
