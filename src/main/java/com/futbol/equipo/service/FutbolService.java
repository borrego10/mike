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

    // Los entrenamientos quedan en memoria mientras la aplicacion esta encendida.
    private final List<Entrenamiento> entrenamientos = new ArrayList<>();

    private static final int CANTIDAD_TITULARES = 5;
    private static final int ENTRENAMIENTOS_REQUERIDOS = 3;

    // Guarda el entrenamiento y calcula el puntaje de cada jugador.
    public Entrenamiento guardarEntrenamiento(Entrenamiento entrenamiento) {
        if (entrenamiento.getJugadores() != null) {
            for (Jugador jugador : entrenamiento.getJugadores()) {
                jugador.calcularPuntaje();
            }
        }

        entrenamientos.add(entrenamiento);
        return entrenamiento;
    }

    // Calcula los 5 titulares cuando ya existen 3 entrenamientos.
    public Map<String, Object> obtenerTitulares() {
        Map<String, Object> respuesta = new LinkedHashMap<>();

        if (entrenamientos.size() < ENTRENAMIENTOS_REQUERIDOS) {
            respuesta.put("mensaje", "No hay suficiente informacion. Se requieren 3 entrenamientos de la semana.");
            respuesta.put("entrenamientosRegistrados", entrenamientos.size());
            respuesta.put("titulares", List.of());
            return respuesta;
        }

        Map<String, List<Double>> puntajesPorJugador = new HashMap<>();

        for (Entrenamiento entrenamiento : entrenamientos) {
            for (Jugador jugador : entrenamiento.getJugadores()) {
                String nombre = jugador.getNombreJugador();
                double puntaje = jugador.getPuntaje();

                if (!puntajesPorJugador.containsKey(nombre)) {
                    puntajesPorJugador.put(nombre, new ArrayList<>());
                }

                puntajesPorJugador.get(nombre).add(puntaje);
            }
        }

        List<TitularDTO> promedios = new ArrayList<>();

        for (Map.Entry<String, List<Double>> entry : puntajesPorJugador.entrySet()) {
            String nombre = entry.getKey();
            List<Double> puntajes = entry.getValue();
            double suma = 0.0;

            for (double puntaje : puntajes) {
                suma += puntaje;
            }

            double promedio = suma / puntajes.size();
            promedio = Math.round(promedio * 100.0) / 100.0;

            promedios.add(new TitularDTO(0, nombre, promedio));
        }

        promedios.sort((a, b) -> Double.compare(b.getPromedioPuntaje(), a.getPromedioPuntaje()));

        List<TitularDTO> titulares = new ArrayList<>();
        int limite = Math.min(CANTIDAD_TITULARES, promedios.size());

        for (int i = 0; i < limite; i++) {
            TitularDTO titular = promedios.get(i);
            titular.setPosicion(i + 1);
            titulares.add(titular);
        }

        respuesta.put("mensaje", "Equipo titular determinado exitosamente.");
        respuesta.put("totalEntrenamientos", entrenamientos.size());
        respuesta.put("titulares", titulares);
        return respuesta;
    }

    public void reiniciar() {
        entrenamientos.clear();
    }
}
