package com.example.pulzy.model

import androidx.compose.ui.graphics.Color

object DemoEvents {
    val topics = listOf(
        "Música",
        "Cultura",
        "Comunidad",
        "Deportes",
        "Tecnología",
        "Vida Nocturna"
    )

    val catalogoTemas = topics.mapIndexed { index, nombre -> Tema(index + 1, nombre) }

    val list: List<Event> = listOf(
        Event(
            id = "evt_1",
            title = "Sesión Acústica Universitaria",
            topic = "Música",
            date = "Viernes 28 de Marzo · 18:00",
            location = "Plaza Abaroa, Sopocachi",
            description = "Artistas emergentes de distintas universidades de La Paz se reúnen para una tarde de música en vivo, indie y folklore acústico al aire libre.",
            organizer = "Colectivo Musical Universitario",
            cardBgColor = Color(0xFFE8DBFF)
        ),
        Event(
            id = "evt_2",
            title = "Ruta Nocturna de Museos y Galerías",
            topic = "Cultura",
            date = "Sábado 29 de Marzo · 19:00",
            location = "Calle Jaén y Centro Histórico",
            description = "Recorrido guiado nocturno con descuentos especiales para estudiantes. Arte colonial, exposiciones contemporáneas y mitos paceños.",
            organizer = "Cultura Paceña Estudiantil",
            cardBgColor = Color(0xFFFFF1C5)
        ),
        Event(
            id = "evt_3",
            title = "Torneo de Fútsal Interfacultades",
            topic = "Deportes",
            date = "Domingo 30 de Marzo · 10:00",
            location = "Cancha Zapata, Miraflores",
            description = "Ven a competir o alentar a las delegaciones universitarias. Habrá música en vivo, trofeos y feria gastronómica juvenil.",
            organizer = "Liga Deportiva Estudiantil",
            cardBgColor = Color(0xFFE1EED4)
        ),
        Event(
            id = "evt_4",
            title = "Hackathon Estudiantil La Paz Innova",
            topic = "Tecnología",
            date = "Viernes 5 de Abril · 09:00",
            location = "Campus Cota Cota, La Paz",
            description = "24 horas intensivas para prototipar soluciones tecnológicas a problemas urbanos de La Paz. Mentores tech, pizza y premios.",
            organizer = "Rama Estudiantil IEEE",
            cardBgColor = Color(0xFFFFE3E3)
        ),
        Event(
            id = "evt_5",
            title = "Picnic & Trueque de Libros y Apuntes",
            topic = "Comunidad",
            date = "Sábado 6 de Abril · 14:00",
            location = "Parque Urbano Central",
            description = "Intercambia libros de texto, guías de estudio y material académico con estudiantes de otras facultades mientras compartes en el parque.",
            organizer = "Red Universitaria La Paz",
            cardBgColor = Color(0xFFD0F4DE)
        ),
        Event(
            id = "evt_6",
            title = "Noche de Jam & Batalla de Bandas",
            topic = "Vida Nocturna",
            date = "Sábado 6 de Abril · 21:00",
            location = "Pub Cultural, San Pedro",
            description = "Presentaciones de bandas locales emergentes. Entrada libre presentando carnet de estudiante universitario.",
            organizer = "Movimiento Universitario",
            cardBgColor = Color(0xFFE5D4FF)
        )
    )

    val eventos = list.associate { ficha ->
        val id = ficha.id.removePrefix("evt_").toInt()
        val local = Establecimiento(id, ficha.organizer, ficha.location, "", AccesoLocal(id, Usuario(id = -id, nombre = ficha.organizer), RolLocal.ADMINISTRADOR))
        val evento = Evento(id, ficha.title, java.util.GregorianCalendar(2025, if (id <= 3) 2 else 3,
            listOf(28, 29, 30, 5, 6, 6)[id - 1], listOf(18, 19, 10, 9, 14, 21)[id - 1], 0).time, 100, java.math.BigDecimal.ZERO,
            catalogoTemas.first { it.nombre == ficha.topic }, local)
        local.crearEvento(evento)
        ficha.id to evento
    }
}
