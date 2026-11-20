package com.example.pulzy.model

import java.math.BigDecimal
import java.util.Date

class Pago internal constructor(val id: Int, val monto: BigDecimal, val referenciaPago: String,
                                val establecimiento: Establecimiento, val fecha: Date = Date()) {
    var estado = EstadoPago.PENDIENTE
        private set
    var comprobante: Comprobante? = null
        private set
    fun confirmar() { check(estado == EstadoPago.PENDIENTE); estado = EstadoPago.CONFIRMADO }
    fun registrarFallo() { check(estado == EstadoPago.PENDIENTE); estado = EstadoPago.FALLIDO }
    fun emitirComprobante(documento: Comprobante) {
        check(estado == EstadoPago.CONFIRMADO && comprobante == null)
        comprobante = documento
    }
}
