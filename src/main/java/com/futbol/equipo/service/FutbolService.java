package com.futbol.equipo.service;

import com.futbol.equipo.model.Entrenamiento;
import com.futbol.equipo.model.Jugador;
import com.futbol.equipo.model.TitularDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FutbolService {

    // lista para guardar los entrenamientos
    private final List<Entrenamiento> entrenamientos = new ArrayList<>();

    private static final int CANTIDAD_TITULARES = 5;
    private static final int ENTRENAMIENTOS_REQUERIDOS = 3;

    // guarda el entrenamiento y calcula el puntaje de cada jugador
    public Entrenamiento guardarEntrenamiento(Entrenamiento entrenamiento) {
        if (entrenamiento.getJugadores() != null) {
            for (Jugador jugador : entrenamiento.getJugadores()) {
                jugador.calcularPuntaje();
            }
        }
        entrenamientos.add(entrenamiento);
        return entrenamiento;
    }

    // metodo principal que saca los 5 titulares
    public Map<String, Object> obtenerTitulares() {
        Map<String, Object> respuesta = new LinkedHashMap<>();

        // validar que se hayan hecho los 3 entrenamientos
        if (entrenamientos.size() < ENTRENAMIENTOS_REQUERIDOS) {
            respuesta.put("mensaje", "No hay suficiente información. Se requieren 3 entrenamientos de la semana.");
            respuesta.put("entrenamientosRegistrados", entrenamientos.size());
            respuesta.put("titulares", List.of());
            return respuesta;
        }

        // agrupar los puntajes de cada jugador en todos los entrenamientos
        Map<String, List<Double>> puntajesPorJugador = new HashMap<>();
        for (Entrenamiento entrenamiento : entrenamientos) {
            for (Jugador jugador : entrenamiento.getJugadores()) {
                puntajesPorJugador
                        .computeIfAbsent(jugador.getNombreJugador(), k -> new ArrayList<>())
                        .add(jugador.getPuntaje());
            }
        }

        // calcular el promedio de cada jugador
        List<TitularDTO> listaPromedios = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : puntajesPorJugador.entrySet()) {
            String nombre = entry.getKey();
            List<Double> notas = entry.getValue();

            double suma = 0.0;
            for (double nota : notas) {
                suma += nota;
            }
            double promedio = Math.round((suma / notas.size()) * 100.0) / 100.0;
            listaPromedios.add(new TitularDTO(0, nombre, promedio));
        }

        // ordenar de mayor a menor
        listaPromedios.sort((a, b) -> Double.compare(b.getPromedioPuntaje(), a.getPromedioPuntaje()));

        // tomar los 5 mejores
        List<TitularDTO> titularesFinales = new ArrayList<>();
        int limite = Math.min(CANTIDAD_TITULARES, listaPromedios.size());
        for (int i = 0; i < limite; i++) {
            TitularDTO titular = listaPromedios.get(i);
            titular.setPosicion(i + 1);
            titularesFinales.add(titular);
        }

        respuesta.put("mensaje", "Equipo titular determinado exitosamente.");
        respuesta.put("totalEntrenamientos", entrenamientos.size());
        respuesta.put("titulares", titularesFinales);
        return respuesta;
    }

    public void reiniciar() {
        entrenamientos.clear();
    }
}
