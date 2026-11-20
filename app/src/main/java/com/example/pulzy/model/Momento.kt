package com.example.pulzy.model

import java.util.Date

class Momento(val id: Int, val autor: Usuario, val fotoFrontal: String, val fotoEntorno: String,
              val descripcion: String, val fechaPublicacion: Date = Date()) {
    private val entradas = mutableListOf<Comentario>()
    val comentarios get() = entradas.toList()
    var eliminado = false
        private set
    fun agregarComentario(comentario: Comentario) {
        check(!eliminado)
        require(entradas.none { it.id == comentario.id })
        entradas.add(comentario)
    }
    fun eliminar() { eliminado = true; entradas.clear() }
}
