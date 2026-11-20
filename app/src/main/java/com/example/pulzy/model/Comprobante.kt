package com.example.pulzy.model

import java.util.Date

data class Comprobante(val id: Int, val numero: String, val fechaEmision: Date, val archivo: String) {
    fun descargar() = archivo
}
