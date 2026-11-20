package com.example.pulzy.model

import java.util.Date

class EventoGuardado(val id: Int, val evento: Evento, val fechaGuardado: Date = Date()) {
    var activo = true
        private set
    fun quitar() { activo = false }
}
