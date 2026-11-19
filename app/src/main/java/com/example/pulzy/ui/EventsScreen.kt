package com.example.pulzy.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
fun EventsScreen(
    savedEventIds: Set<String>,
    onToggleSave: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialFilter: String = "Todos"
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf(initialFilter) }
    var selectedEventForDetail by rememberSaveable { mutableStateOf<Event?>(null) }

    // Lista de opciones de filtros de tema más la sección "★ Guardados"
    val filterOptions = listOf("Todos", "★ Guardados") + DemoEvents.topics

    // Filtrar la lista de los 6 eventos de acuerdo a la sección elegida y la búsqueda
    val filteredEvents = remember(selectedFilter, searchQuery, savedEventIds) {
        val candidates = when (selectedFilter) {
            "★ Guardados" -> DemoEvents.list.filter { event -> savedEventIds.contains(event.id) }
            "Todos" -> DemoEvents.list
            else -> DemoEvents.list.filter { event -> event.topic.equals(selectedFilter, ignoreCase = true) }
        }

        if (searchQuery.isBlank()) {
            candidates
        } else {
            val query = searchQuery.trim().lowercase()
            candidates.filter { event ->
                event.title.lowercase().contains(query) ||
                        event.topic.lowercase().contains(query) ||
                        event.description.lowercase().contains(query) ||
                        event.location.lowercase().contains(query) ||
                        event.organizer.lowercase().contains(query)
            }
        }
    }

    Page(modifier = modifier) {
        Heading(
            title = "Encuentra tu plan",
            subtitle = "Explora la agenda universitaria de La Paz, busca y guarda tus eventos."
        )

        // Campo de búsqueda en vivo
        PulziField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = "Buscar evento o lugar",
            placeholder = "Ej. Sopocachi, acústica, museos..."
        )

        // Fila horizontal de Filtros de Tema y Sección Guardados
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtrar por tema o guardados",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Ink
                )
                if (selectedFilter != "Todos" || searchQuery.isNotEmpty()) {
                    TextButton(
                        onClick = {
                            selectedFilter = "Todos"
                            searchQuery = ""
                        },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Limpiar filtros", fontSize = 12.sp, color = Violet, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterOptions.forEach { filterName ->
                    val isSelected = selectedFilter == filterName
                    val isSavedTab = filterName == "★ Guardados"

                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filterName },
                        label = {
                            Text(
                                text = if (isSavedTab) "$filterName (${savedEventIds.size})" else filterName,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (isSavedTab) Yellow else Violet,
                            selectedLabelColor = if (isSavedTab) Ink else Color.White,
                            containerColor = Color.White,
                            labelColor = Ink
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSavedTab) Yellow else Ink.copy(alpha = 0.2f),
                            selectedBorderColor = if (isSavedTab) Yellow else Violet,
                            borderWidth = 1.5.dp
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }
        }

        // Título de la sección resultante
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (selectedFilter) {
                    "★ Guardados" -> "Sección Guardados"
                    "Todos" -> "Todos los eventos"
                    else -> "Tema: $selectedFilter"
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Ink
            )

            Surface(
                color = Violet.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "${filteredEvents.size} plan(es)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Violet,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Renderizado de lista de eventos o estado vacío
        if (filteredEvents.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Ink.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = if (selectedFilter == "★ Guardados") "☆" else "🔍",
                        fontSize = 40.sp
                    )
                    Text(
                        text = if (selectedFilter == "★ Guardados") {
                            "No tienes eventos en la sección Guardados"
                        } else {
                            "No encontramos resultados"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Ink
                    )
                    Text(
                        text = if (selectedFilter == "★ Guardados") {
                            "Explora la agenda y haz clic en el botón 'Guardar' para agregar tus eventos preferidos aquí."
                        } else {
                            "Intenta buscar con otros términos o cambia el filtro de tema."
                        },
                        fontSize = 14.sp,
                        color = Ink.copy(alpha = 0.7f),
                        lineHeight = 20.sp
                    )

                    if (selectedFilter == "★ Guardados") {
                        Button(
                            onClick = { selectedFilter = "Todos" },
                            colors = ButtonDefaults.buttonColors(containerColor = Violet),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Ver todos los eventos", fontWeight = FontWeight.Bold)
                        }
                    } else if (searchQuery.isNotEmpty()) {
                        Button(
                            onClick = { searchQuery = "" },
                            colors = ButtonDefaults.buttonColors(containerColor = Violet),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Borrar búsqueda", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            filteredEvents.forEach { event ->
                val isSaved = savedEventIds.contains(event.id)
                EventCard(
                    event = event,
                    isSaved = isSaved,
                    onToggleSave = { onToggleSave(event.id) },
                    onDetailClick = { selectedEventForDetail = event }
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

@Preview(name = "EventsScreen Preview - Todos", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun EventsScreenTodosPreview() {
    PulzyTheme {
        EventsScreen(
            savedEventIds = setOf("evt_1"),
            onToggleSave = {}
        )
    }
}

@Preview(name = "EventsScreen Preview - Guardados", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun EventsScreenGuardadosPreview() {
    PulzyTheme {
        EventsScreen(
            savedEventIds = setOf("evt_1", "evt_3"),
            onToggleSave = {},
            initialFilter = "★ Guardados"
        )
    }
}
