package com.example.pulzy.model

import java.math.BigDecimal
import java.util.Date

class Evento(val id: Int, val titulo: String, val fecha: Date, val cupo: Int,
             val precioPorPersona: BigDecimal, val tema: Tema, val establecimiento: Establecimiento) {
    init { require(cupo >= 0 && precioPorPersona.signum() >= 0) }
    private val inscripciones = mutableListOf<Reserva>()
    val reservas get() = inscripciones.toList()
    var abierto = true
        private set
    fun cuposDisponibles() = cupo - inscripciones.sumOf { it.cantidadPersonas }
    fun cerrarInscripciones() { abierto = false }
    fun reservar(id: Int, plan: Plan, usuarios: List<Usuario>, fechaLimitePago: Date): Reserva {
        check(abierto && plan.reserva == null)
        require(plan.evento == null || plan.evento === this)
        require(usuarios.size in 1..cuposDisponibles() && inscripciones.none { it.id == id })
        require(usuarios.distinctBy { it.id }.size == usuarios.size)
        val participantes = usuarios.map { ParticipanteReserva(it.id, it, precioPorPersona, establecimiento) }
        return Reserva(id, fechaLimitePago, participantes).also {
            inscripciones.add(it)
            plan.registrarReserva(it)
        }
    }
}
