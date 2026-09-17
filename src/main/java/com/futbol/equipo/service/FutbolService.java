package com.futbol.equipo.service;

import com.futbol.equipo.model.Entrenamiento;
import com.futbol.equipo.model.Jugador;
import com.futbol.equipo.model.TitularDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FutbolService {

    // Lista en memoria para almacenar los entrenamientos de la semana
    private final List<Entrenamiento> entrenamientos = new ArrayList<>();
    
    // Puede escalar a fútbol 11 cambiando este valor
    private static final int CANTIDAD_TITULARES = 5;
    private static final int ENTRENAMIENTOS_REQUERIDOS = 3;

    /**
     * Almacena la información de cada entrenamiento y calcula el puntaje de cada jugador.
     */
    public Entrenamiento guardarEntrenamiento(Entrenamiento entrenamiento) {
        if (entrenamiento.getJugadores() != null) {
            for (Jugador jugador : entrenamiento.getJugadores()) {
                jugador.calcularPuntaje();
            }
        }
        entrenamientos.add(entrenamiento);
        return entrenamiento;
    }

    /**
     * Determina el equipo titular con los 5 mejores jugadores.
     * Solo retorna titulares si se completaron los 3 entrenamientos de la semana.
     */
    public Map<String, Object> obtenerTitulares() {
        Map<String, Object> respuesta = new LinkedHashMap<>();

        // Regla: Validar si se completaron los 3 entrenamientos
        if (entrenamientos.size() < ENTRENAMIENTOS_REQUERIDOS) {
            respuesta.put("mensaje", "No hay suficiente información. Se requieren 3 entrenamientos de la semana.");
            respuesta.put("entrenamientosRegistrados", entrenamientos.size());
            respuesta.put("titulares", List.of());
            return respuesta;
        }

        // Agrupar los puntajes de cada jugador a lo largo de los entrenamientos
        Map<String, List<Double>> puntajesPorJugador = new HashMap<>();
        for (Entrenamiento entrenamiento : entrenamientos) {
            for (Jugador jugador : entrenamiento.getJugadores()) {
                puntajesPorJugador
                        .computeIfAbsent(jugador.getNombreJugador(), k -> new ArrayList<>())
                        .add(jugador.getPuntaje());
            }
        }

        // Calcular el promedio de cada jugador
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

        // Ordenar de mayor a menor según el promedio
        listaPromedios.sort((a, b) -> Double.compare(b.getPromedioPuntaje(), a.getPromedioPuntaje()));

        // Tomar los 5 primeros para el equipo titular
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

    public List<Entrenamiento> listarEntrenamientos() {
        return entrenamientos;
    }

    public void reiniciar() {
        entrenamientos.clear();
    }
}
