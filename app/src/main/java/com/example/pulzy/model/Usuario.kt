package com.example.pulzy.model

data class Usuario(
    val nombre: String = "Estudiante PULZI",
    val universidad: String = "Universidad Mayor de San Andrés",
    val fotoPerfil: String? = null,
    val id: Int = 1,
    val correo: String = "",
    val carrera: String = "",
    val biografia: String = "",
    val estado: EstadoUsuario = EstadoUsuario.ACTIVO,
    val cuenta: CuentaAcceso = CuentaAcceso(id, correo),
    val verificacion: VerificacionUniversitaria? = null,
    val preferencias: List<PreferenciaTema> = DemoEvents.catalogoTemas.take(3).map { PreferenciaTema(it.id, it) },
    val guardados: List<EventoGuardado> = emptyList(),
    val planes: List<Plan> = emptyList()
) {
    val fotoUri get() = fotoPerfil
    val temas get() = preferencias.filter { it.activa }.map { it.tema.nombre }.toSet()
    val eventosGuardados get() = guardados.filter { it.activo }.map { "evt_${it.evento.id}" }.toSet()

    fun cambiarFoto(foto: String?) = copy(fotoPerfil = foto)
    fun crearPlan(plan: Plan): Usuario {
        require(plan.creadorId == id && planes.none { it.id == plan.id })
        return copy(planes = planes + plan)
    }
    fun elegirTemas(nombres: Set<String>) = copy(preferencias = DemoEvents.catalogoTemas
        .filter { it.activo && it.nombre in nombres }
        .map { tema -> preferencias.firstOrNull { it.activa && it.tema.id == tema.id } ?: PreferenciaTema(tema.id, tema) })

    fun alternarGuardado(evento: Evento): Usuario {
        val otros = guardados.filter { it.evento.id != evento.id }
        return copy(guardados = if (guardados.any { it.activo && it.evento.id == evento.id }) otros
            else otros + EventoGuardado(evento.id, evento))
    }
}
