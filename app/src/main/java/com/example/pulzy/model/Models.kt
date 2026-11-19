package com.example.pulzy.model

// Datos temporales del estudiante durante esta demostración.
data class Usuario(
    val nombre: String = "Estudiante PULZI",
    val universidad: String = "Universidad Mayor de San Andrés",
    val fotoUri: String? = null,
    val temas: Set<String> = setOf("Música", "Cultura", "Comunidad"),
    val eventosGuardados: Set<String> = emptySet()
)

