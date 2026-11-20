package com.example.pulzy.model

class ParticipantePlan(val id: Int, val usuario: Usuario) {
    var estado = EstadoParticipante.PENDIENTE
        private set
    fun confirmarAsistencia() { estado = EstadoParticipante.CONFIRMADO }
    fun cancelarAsistencia() { estado = EstadoParticipante.CANCELADO }
}
