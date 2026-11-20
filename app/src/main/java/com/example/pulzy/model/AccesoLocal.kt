package com.example.pulzy.model

class AccesoLocal(val id: Int, val usuario: Usuario, val rol: RolLocal, activo: Boolean = true) {
    var activo = activo
        private set

    fun activar() { activo = true }
    fun desactivar() { activo = false }
    fun puedeGestionarEventos() = activo && rol != RolLocal.CONSULTA
}
