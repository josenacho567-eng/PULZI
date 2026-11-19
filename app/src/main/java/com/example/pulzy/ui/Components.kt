package com.example.pulzy.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulzy.ui.theme.*

enum class PulziButtonVariant {
    Primary,
    Secondary,
    Outline
}

@Composable
fun PulziButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: PulziButtonVariant = PulziButtonVariant.Primary,
    enabled: Boolean = true
) {
    val (containerColor, contentColor) = when (variant) {
        PulziButtonVariant.Primary -> Violet to Color.White
        PulziButtonVariant.Secondary -> Yellow to Ink
        PulziButtonVariant.Outline -> Color.Transparent to Violet
    }
    val border = if (variant == PulziButtonVariant.Outline) {
        BorderStroke(2.dp, Violet)
    } else null

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        ),
        border = border,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Composable
fun PulziField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isPassword: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Ink
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(16.dp),
            placeholder = placeholder?.let {
                { Text(it, color = Ink.copy(alpha = 0.45f), fontSize = 15.sp) }
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = when {
                trailingIcon != null -> trailingIcon
                isPassword -> {
                    {
                        TextButton(
                            onClick = { passwordVisible = !passwordVisible },
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        ) {
                            Text(
                                text = if (passwordVisible) "Ocultar" else "Ver",
                                color = Violet,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                else -> null
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Ink,
                unfocusedTextColor = Ink,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Violet,
                focusedBorderColor = Violet,
                unfocusedBorderColor = Ink.copy(alpha = 0.2f)
            )
        )
    }
}

@Composable
private fun Page(modifier: Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        content = content
    )
}

@Composable
private fun Heading(title: String, subtitle: String) {
    Text(title, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
    Text(subtitle, style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onEvents: () -> Unit) {
    Page(modifier) {
        Text("PULZI  ✦", color = Violet, fontSize = 24.sp, fontWeight = FontWeight.Black)
        Text("LA PAZ, BOLIVIA  •  A TU RITMO", color = Violet, fontSize = 12.sp)
        Heading("La vida uni\nse vive afuera.", "Descubre planes, conecta y haz tuya la ciudad.")
        Card(colors = CardDefaults.cardColors(containerColor = Violet), shape = RoundedCornerShape(28.dp)) {
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("TU PRÓXIMO GRAN PLAN", color = Yellow, fontWeight = FontWeight.Bold)
                Text("Menos rutina.\nMás historias.", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text("Cultura, música y encuentros para estudiantes paceños.", color = Color.White)
                Button(onClick = onEvents, colors = ButtonDefaults.buttonColors(containerColor = Yellow, contentColor = Ink)) {
                    Text("Explorar eventos  →")
                }
            }
        }
        Text("Conecta con lo que te mueve", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Música", "Cultura", "Comunidad").forEach {
                SuggestionChip(onClick = onEvents, label = { Text(it) })
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE1EED4))) {
            Column(Modifier.padding(20.dp)) {
                Text("Hecho para tu altura ✦", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Nuevas experiencias a 3.600 metros. Empieza por descubrir tu próximo encuentro.")
            }
        }
    }
}

@Composable
fun EventsScreen(modifier: Modifier = Modifier) {
    var filter by rememberSaveable { mutableStateOf("Todos") }
    var detail by rememberSaveable { mutableStateOf<String?>(null) }
    val events = listOf(
        Triple("Música", "Sesión acústica universitaria", "Viernes · 18:00 · Sopocachi"),
        Triple("Cultura", "Ruta de museos", "Sábado · 10:00 · Centro paceño"),
        Triple("Comunidad", "Encuentro entre estudiantes", "Domingo · 15:00 · Miraflores")
    )
    Page(modifier) {
        Heading("Encuentra tu plan", "La Paz tiene algo para ti.")
        Text("Agenda de demostración", color = Violet)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Todos", "Música", "Cultura").forEach { category ->
                FilterChip(selected = filter == category, onClick = { filter = category }, label = { Text(category) })
            }
        }
        events.filter { filter == "Todos" || it.first == filter }.forEachIndexed { index, event ->
            Card(
                colors = CardDefaults.cardColors(containerColor = if (index % 2 == 0) Yellow else Color(0xFFE8DBFF)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(event.first.uppercase(), color = Violet, fontWeight = FontWeight.Bold)
                    Text(event.second, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(event.third)
                    TextButton(onClick = { detail = event.second }) { Text("Ver el plan  →") }
                }
            }
        }
    }
    detail?.let { title ->
        AlertDialog(
            onDismissRequest = { detail = null },
            title = { Text(title) },
            text = { Text("Plan de ejemplo para explorar PULZI. Entrada libre para estudiantes. Los horarios y lugares son ilustrativos; no se realizan reservas.") },
            confirmButton = { TextButton(onClick = { detail = null }) { Text("Entendido") } }
        )
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogout: (() -> Unit)? = null
) {
    var notifications by rememberSaveable { mutableStateOf(false) }
    Page(modifier) {
        Heading("Tu espacio", "Tu vida universitaria, a tu manera.")
        Card(colors = CardDefaults.cardColors(containerColor = Violet)) {
            Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("☺", fontSize = 48.sp, color = Yellow)
                Text("Hola, estudiante", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("La Paz · Perfil de demostración", color = Color.White)
            }
        }
        Text("Tus intereses", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Música · Cultura · Comunidad")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Recordatorios de ejemplo", modifier = Modifier.weight(1f))
            Switch(checked = notifications, onCheckedChange = { notifications = it })
        }
        Text(if (notifications) "Preferencia activada en esta sesión. No se envían notificaciones." else "Explora a tu ritmo.")

        if (onLogout != null) {
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Violet),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Violet),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Cerrar sesión  (Volver al ingreso)", fontWeight = FontWeight.Bold)
            }
        }

        HorizontalDivider()
        Text("PULZI ✦\nHecho para estudiantes de La Paz.", color = Violet)
    }
}

@Preview(name = "Inicio", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenPreview() {
    PulzyTheme {
        Surface {
            HomeScreen(onEvents = {})
        }
    }
}

@Preview(name = "Eventos", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun EventsScreenPreview() {
    PulzyTheme {
        Surface {
            EventsScreen()
        }
    }
}

@Preview(name = "Perfil", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ProfileScreenPreview() {
    PulzyTheme {
        Surface {
            ProfileScreen()
        }
    }
}
