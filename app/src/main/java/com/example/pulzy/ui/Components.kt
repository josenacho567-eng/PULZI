package com.example.pulzy.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulzy.model.Event
import com.example.pulzy.ui.theme.*

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
fun Page(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
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
fun Heading(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Ink, lineHeight = 36.sp)
        Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = Ink.copy(alpha = 0.75f))
    }
}

@Composable
fun EventCard(
    event: Event,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = event.cardBgColor),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.5.dp, Ink.copy(alpha = 0.08f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDetailClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Fila superior: Chip de Tema y Botón Guardar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Violet,
                    shape = RoundedCornerShape(12.dp),
                    contentColor = Color.White
                ) {
                    Text(
                        text = "✦ ${event.topic.uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        letterSpacing = 0.5.sp
                    )
                }

                Button(
                    onClick = onToggleSave,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSaved) Violet else Color.White,
                        contentColor = if (isSaved) Yellow else Ink
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = if (!isSaved) BorderStroke(1.dp, Ink.copy(alpha = 0.2f)) else null,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = if (isSaved) "★ Guardado" else "☆ Guardar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Título
            Text(
                text = event.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Ink,
                lineHeight = 28.sp
            )

            // Organizador y detalles de tiempo/lugar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Por ${event.organizer}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Violet
                )
                Text(
                    text = "📅 ${event.date}",
                    fontSize = 13.sp,
                    color = Ink.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "📍 ${event.location}",
                    fontSize = 13.sp,
                    color = Ink.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
            }

            // Descripción breve
            Text(
                text = event.description,
                fontSize = 14.sp,
                color = Ink.copy(alpha = 0.75f),
                lineHeight = 20.sp,
                maxLines = 2
            )

            HorizontalDivider(color = Ink.copy(alpha = 0.1f))

            // Fila inferior: Tag Entrada Libre y Ver detalles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (event.isFree) {
                    Surface(
                        color = Yellow,
                        shape = RoundedCornerShape(8.dp),
                        contentColor = Ink
                    ) {
                        Text(
                            text = "Entrada libre",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    Spacer(Modifier.width(1.dp))
                }

                TextButton(
                    onClick = onDetailClick,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Ver detalles  →",
                        color = Violet,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailDialog(
    event: Event,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(
                    color = Violet,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = Color.White
                ) {
                    Text(
                        text = "✦ ${event.topic.uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = event.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink,
                    lineHeight = 28.sp
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Organiza: ${event.organizer}", color = Violet, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text("📅 ${event.date}", fontSize = 14.sp, color = Ink)
                Text("📍 ${event.location}", fontSize = 14.sp, color = Ink)
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Ink.copy(alpha = 0.1f))
                Text(event.description, fontSize = 14.sp, color = Ink.copy(alpha = 0.85f), lineHeight = 20.sp)
                Text(
                    "✦ Plan universitario de demostración. Entrada libre para estudiantes.",
                    fontSize = 12.sp,
                    color = Ink.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onToggleSave()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSaved) Violet else Yellow,
                    contentColor = if (isSaved) Color.White else Ink
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (isSaved) "★ Quitar de guardados" else "☆ Guardar evento", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar", color = Ink)
            }
        },
        containerColor = Cream,
        shape = RoundedCornerShape(24.dp)
    )
}
