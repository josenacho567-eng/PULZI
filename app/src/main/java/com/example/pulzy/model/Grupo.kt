package com.example.pulzy.model

class Grupo(val id: Int, val nombre: String, val descripcion: String, fundador: Usuario) {
    private val integrantes = mutableMapOf(fundador.id to MiembroGrupo(fundador.id, fundador, RolGrupo.ADMINISTRADOR).apply { aceptarInvitacion() })
    val miembros get() = integrantes.values.toList()
    fun agregarMiembro(usuario: Usuario) {
        require(integrantes[usuario.id]?.estado in listOf(null, EstadoMiembro.RETIRADO))
        integrantes[usuario.id] = MiembroGrupo(usuario.id, usuario, RolGrupo.MIEMBRO)
    }
    fun totalMiembros() = miembros.count { it.estado == EstadoMiembro.ACTIVO }
}
