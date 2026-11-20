package com.example.pulzy.model

import java.util.Date

class PreferenciaTema(val id: Int, val tema: Tema, val fechaSeleccion: Date = Date()) {
    var activa = true
        private set
    fun quitar() { activa = false }
}
