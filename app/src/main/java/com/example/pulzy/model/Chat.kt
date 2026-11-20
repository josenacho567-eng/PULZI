package com.example.pulzy.model

import java.util.Date

class Chat internal constructor(val id: Int, private val conexion: Conexion, val fechaCreacion: Date = Date()) {
    private val historial = mutableListOf<Mensaje>()
    val mensajes get() = historial.filterNot { it.eliminado }
    fun enviarMensaje(mensaje: Mensaje) {
        check(conexion.estado == EstadoConexion.ACEPTADA)
        require(mensaje.autor.id in listOf(conexion.solicitante.id, conexion.destinatario.id))
        require(historial.none { it.id == mensaje.id })
        historial.add(mensaje)
    }
    fun totalMensajes() = mensajes.size
}
