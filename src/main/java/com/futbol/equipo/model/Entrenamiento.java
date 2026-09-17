package com.futbol.equipo.model;

import java.util.ArrayList;
import java.util.List;

public class Entrenamiento {

    private int numeroEntrenamiento;
    private String fecha;
    private List<Jugador> jugadores = new ArrayList<>();

    public Entrenamiento() {
    }

    public Entrenamiento(int numeroEntrenamiento, String fecha, List<Jugador> jugadores) {
        this.numeroEntrenamiento = numeroEntrenamiento;
        this.fecha = fecha;
        this.jugadores = jugadores;
    }

    public int getNumeroEntrenamiento() {
        return numeroEntrenamiento;
    }

    public void setNumeroEntrenamiento(int numeroEntrenamiento) {
        this.numeroEntrenamiento = numeroEntrenamiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
