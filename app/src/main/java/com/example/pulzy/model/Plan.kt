package com.example.pulzy.model

import java.util.Date

class Plan(val id: Int, val creadorId: Int, val titulo: String, val descripcion: String, val fecha: Date,
           val grupo: Grupo? = null, val evento: Evento? = null, estado: EstadoPlan = EstadoPlan.ABIERTO) {
    var estado = estado
        private set

    fun cerrar() { estado = EstadoPlan.CERRADO }

    private val inscritos = mutableMapOf<Int, ParticipantePlan>()
    val participantes get() = inscritos.values.toList()
    var reserva: Reserva? = null
        private set

    internal fun registrarReserva(nueva: Reserva) {
        check(reserva == null)
        reserva = nueva
    }

    fun agregarParticipante(usuario: Usuario) {
        check(estado == EstadoPlan.ABIERTO)
        inscritos.getOrPut(usuario.id) { ParticipantePlan(usuario.id, usuario) }
    }
    fun totalConfirmados() = participantes.count { it.estado == EstadoParticipante.CONFIRMADO }
}
