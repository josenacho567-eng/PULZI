package com.example.pulzy.model

class Establecimiento(val id: Int, val nombre: String, val direccion: String, val cuentaDeCobro: String,
                      responsable: AccesoLocal) {
    private val autorizaciones = mutableListOf(responsable)
    val accesos get() = autorizaciones.toList()

    fun agregarAcceso(acceso: AccesoLocal) {
        require(autorizaciones.none { it.id == acceso.id || it.usuario.id == acceso.usuario.id })
        autorizaciones.add(acceso)
    }

    private val publicados = mutableMapOf<Int, Evento>()
    fun crearEvento(evento: Evento) {
        require(evento.establecimiento === this && evento.id !in publicados)
        publicados[evento.id] = evento
    }
    fun consultarReservas() = publicados.values.sumOf { it.reservas.size }
}
