package com.example.pulzy.model

import java.util.Date

class Mensaje(val id: Int, val autor: Usuario, val contenido: String, val tipo: TipoMensaje,
              val fechaEnvio: Date = Date()) {
    var eliminado = false
        private set
    fun eliminar() { eliminado = true }
}
