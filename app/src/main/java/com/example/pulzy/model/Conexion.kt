package com.example.pulzy.model

import java.util.Date

class Conexion(val id: Int, val solicitante: Usuario, val destinatario: Usuario, val fechaSolicitud: Date = Date()) {
    init { require(solicitante.id != destinatario.id) }
    var estado = EstadoConexion.PENDIENTE
        private set
    var chat: Chat? = null
        private set
    fun aceptar() {
        check(estado == EstadoConexion.PENDIENTE)
        estado = EstadoConexion.ACEPTADA
        chat = Chat(id, this)
    }
    fun bloquear() { estado = EstadoConexion.BLOQUEADA }
}
