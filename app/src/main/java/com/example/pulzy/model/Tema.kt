package com.example.pulzy.model

class Tema(val id: Int, val nombre: String) {
    var activo = true
        private set
    fun desactivar() { activo = false }
}
