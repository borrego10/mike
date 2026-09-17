package com.futbol.equipo;

import com.futbol.equipo.model.Entrenamiento;
import com.futbol.equipo.model.Jugador;
import com.futbol.equipo.model.TitularDTO;
import com.futbol.equipo.service.FutbolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FutbolServiceTest {

    private FutbolService futbolService;

    @BeforeEach
    void setUp() {
        futbolService = new FutbolService();
    }

    @Test
    @DisplayName("Debe calcular correctamente el puntaje del jugador según la fórmula (20%, 30%, 50%)")
    void testCalculoPuntajeJugador() {
        // Datos oficiales de la prueba técnica para el entrenamiento #1:
        // Jugador1: Potencia 10, Velocidad 5, Pases 25 -> Resultado esperado: 16.0
        Jugador jugador1 = new Jugador("Jugador1", 10.0, 5.0, 25);
        assertEquals(16.0, jugador1.getPuntaje(), 0.01);

        // Jugador3: Potencia 15, Velocidad 3, Pases 30 -> Resultado esperado: 18.9
        Jugador jugador3 = new Jugador("Jugador3", 15.0, 3.0, 30);
        assertEquals(18.9, jugador3.getPuntaje(), 0.01);
    }

    @Test
    @DisplayName("Debe retornar mensaje de información insuficiente si hay menos de 3 entrenamientos")
    void testMenosDeTresEntrenamientos() {
        // Solo registramos 2 entrenamientos
        futbolService.guardarEntrenamiento(new Entrenamiento(1, "2026-09-17", List.of(new Jugador("Jugador1", 10, 5, 25))));
        futbolService.guardarEntrenamiento(new Entrenamiento(2, "2026-09-18", List.of(new Jugador("Jugador1", 12, 6, 24))));

        Map<String, Object> resultado = futbolService.obtenerTitulares();

        assertTrue(resultado.get("mensaje").toString().contains("No hay suficiente información"));
        List<?> titulares = (List<?>) resultado.get("titulares");
        assertTrue(titulares.isEmpty());
    }

    @Test
    @DisplayName("Debe seleccionar los 5 titulares ordenados de mayor a menor promedio con 3 entrenamientos")
    @SuppressWarnings("unchecked")
    void testSeleccionTitularesTop5() {
        // Entrenamiento 1 (7 jugadores)
        Entrenamiento e1 = new Entrenamiento(1, "2026-09-17", List.of(
                new Jugador("Jugador1", 10.0, 5.0, 25), // 16.0
                new Jugador("Jugador2", 16.0, 5.0, 20), // 14.7
                new Jugador("Jugador3", 15.0, 3.0, 30), // 18.9
                new Jugador("Jugador4", 12.0, 4.0, 18), // 12.6
                new Jugador("Jugador5", 11.0, 3.0, 19), // 12.6
                new Jugador("Jugador6", 9.0,  3.0, 22), // 13.7
                new Jugador("Jugador7", 10.0, 2.0, 24)  // 14.6
        ));

        // Entrenamiento 2 (mismos valores para verificar promedio exacto)
        Entrenamiento e2 = new Entrenamiento(2, "2026-09-19", List.of(
                new Jugador("Jugador1", 10.0, 5.0, 25),
                new Jugador("Jugador2", 16.0, 5.0, 20),
                new Jugador("Jugador3", 15.0, 3.0, 30),
                new Jugador("Jugador4", 12.0, 4.0, 18),
                new Jugador("Jugador5", 11.0, 3.0, 19),
                new Jugador("Jugador6", 9.0,  3.0, 22),
                new Jugador("Jugador7", 10.0, 2.0, 24)
        ));

        // Entrenamiento 3
        Entrenamiento e3 = new Entrenamiento(3, "2026-09-21", List.of(
                new Jugador("Jugador1", 10.0, 5.0, 25),
                new Jugador("Jugador2", 16.0, 5.0, 20),
                new Jugador("Jugador3", 15.0, 3.0, 30),
                new Jugador("Jugador4", 12.0, 4.0, 18),
                new Jugador("Jugador5", 11.0, 3.0, 19),
                new Jugador("Jugador6", 9.0,  3.0, 22),
                new Jugador("Jugador7", 10.0, 2.0, 24)
        ));

        futbolService.guardarEntrenamiento(e1);
        futbolService.guardarEntrenamiento(e2);
        futbolService.guardarEntrenamiento(e3);

        Map<String, Object> resultado = futbolService.obtenerTitulares();
        List<TitularDTO> titulares = (List<TitularDTO>) resultado.get("titulares");

        // Debe seleccionar exactamente 5 titulares
        assertEquals(5, titulares.size());

        // Debe estar ordenado de mayor a menor
        assertEquals("Jugador3", titulares.get(0).getNombreJugador());
        assertEquals(18.9, titulares.get(0).getPromedioPuntaje(), 0.01);
        assertEquals(1, titulares.get(0).getPosicion());

        assertEquals("Jugador1", titulares.get(1).getNombreJugador());
        assertEquals(16.0, titulares.get(1).getPromedioPuntaje(), 0.01);

        assertEquals("Jugador2", titulares.get(2).getNombreJugador());
        assertEquals(14.7, titulares.get(2).getPromedioPuntaje(), 0.01);

        assertEquals("Jugador7", titulares.get(3).getNombreJugador());
        assertEquals(14.6, titulares.get(3).getPromedioPuntaje(), 0.01);

        assertEquals("Jugador6", titulares.get(4).getNombreJugador());
        assertEquals(13.7, titulares.get(4).getPromedioPuntaje(), 0.01);
    }
}
