package com.example.pulzy.model

import java.util.Date

class Reserva internal constructor(val id: Int, val fechaLimitePago: Date, participantes: List<ParticipanteReserva>) {
    private val aportantes = participantes.toList()
    val participantes get() = aportantes.toList()

    var estado = EstadoReserva.PENDIENTE
        private set
    val cantidadPersonas get() = aportantes.size
    fun totalPagado() = aportantes.sumOf { it.totalPagado() }
    fun estaCompleta() = aportantes.all { it.estaPagado() }
    fun confirmar() { check(estaCompleta()); estado = EstadoReserva.CONFIRMADA }
}
