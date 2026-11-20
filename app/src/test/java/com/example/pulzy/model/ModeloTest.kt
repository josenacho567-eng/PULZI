package com.example.pulzy.model

import java.math.BigDecimal
import java.util.Date
import org.junit.Assert.*
import org.junit.Test

class ModeloTest {
    @Test fun guardarYQuitarUnEventoDelCatalogo() {
        val evento = DemoEvents.eventos.getValue("evt_1")
        val perfil = Usuario().alternarGuardado(evento)
        assertEquals(setOf("evt_1"), perfil.eventosGuardados)
        assertTrue(perfil.alternarGuardado(evento).eventosGuardados.isEmpty())
    }

    @Test fun laReservaSoloCuentaLosPagosConfirmados() {
        val usuario = Usuario()
        val local = Establecimiento(1, "Sala", "Centro", "cuenta", AccesoLocal(1, usuario, RolLocal.ADMINISTRADOR))
        val evento = Evento(1, "Concierto", Date(), 2, BigDecimal.TEN, Tema(1, "Música"), local)
        local.crearEvento(evento)
        val plan = Plan(1, usuario.id, "Salida", "", Date(), evento = evento)
        val reserva = evento.reservar(1, plan, listOf(usuario), Date())
        val participante = reserva.participantes.single()
        participante.registrarIntento(1, BigDecimal.TEN, "a").registrarFallo()
        assertFalse(reserva.estaCompleta())
        participante.registrarIntento(2, BigDecimal.TEN, "b").confirmar()
        reserva.confirmar()
        assertEquals(BigDecimal.TEN, reserva.totalPagado())
        assertEquals(EstadoReserva.CONFIRMADA, reserva.estado)
        assertEquals(1, evento.cuposDisponibles())
        assertEquals(1, local.consultarReservas())
    }
}
