package com.example.pulzy.model

import java.math.BigDecimal

class ParticipanteReserva internal constructor(val id: Int, val usuario: Usuario, val montoAsignado: BigDecimal,
                                               private val establecimiento: Establecimiento) {
    private val intentos = mutableListOf<Pago>()
    val pagos get() = intentos.toList()
    val estado get() = if (estaPagado()) EstadoAporte.PAGADO else EstadoAporte.PENDIENTE
    fun totalPagado() = intentos.filter { it.estado == EstadoPago.CONFIRMADO }.sumOf { it.monto }
    fun estaPagado() = totalPagado() >= montoAsignado
    fun registrarIntento(id: Int, monto: BigDecimal, referencia: String): Pago {
        require(intentos.none { it.id == id || it.referenciaPago == referencia })
        val pendiente = intentos.filter { it.estado != EstadoPago.FALLIDO }.sumOf { it.monto }
        require(monto.signum() > 0 && monto + pendiente <= montoAsignado)
        return Pago(id, monto, referencia, establecimiento).also { intentos.add(it) }
    }
}
