package com.example.pulzy.model

import java.util.Date

class Comentario(val id: Int, val autor: Usuario, contenido: String, val fecha: Date = Date()) {
    var contenido = contenido
        private set
    fun editar(contenido: String) { require(contenido.isNotBlank()); this.contenido = contenido }
}
