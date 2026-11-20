package com.example.pulzy.model

import java.util.Date

class VerificacionUniversitaria(val id: Int, val correoUniversitario: String) {
    var estado = EstadoVerificacion.PENDIENTE
        private set
    var fechaVerificacion: Date? = null
        private set

    fun aprobar() { estado = EstadoVerificacion.APROBADA; fechaVerificacion = Date() }
    fun rechazar() { estado = EstadoVerificacion.RECHAZADA; fechaVerificacion = Date() }
}
