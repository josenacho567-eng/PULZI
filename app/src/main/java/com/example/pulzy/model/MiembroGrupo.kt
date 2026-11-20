package com.example.pulzy.model

class MiembroGrupo(val id: Int, val usuario: Usuario, val rol: RolGrupo) {
    var estado = EstadoMiembro.INVITADO
        private set
    fun aceptarInvitacion() { check(estado == EstadoMiembro.INVITADO); estado = EstadoMiembro.ACTIVO }
    fun salir() { estado = EstadoMiembro.RETIRADO }
}
