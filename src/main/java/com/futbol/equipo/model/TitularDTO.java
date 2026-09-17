package com.futbol.equipo.model;

public class TitularDTO {

    private int posicion;
    private String nombreJugador;
    private double promedioPuntaje;

    public TitularDTO() {
    }

    public TitularDTO(int posicion, String nombreJugador, double promedioPuntaje) {
        this.posicion = posicion;
        this.nombreJugador = nombreJugador;
        this.promedioPuntaje = promedioPuntaje;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public double getPromedioPuntaje() {
        return promedioPuntaje;
    }

    public void setPromedioPuntaje(double promedioPuntaje) {
        this.promedioPuntaje = promedioPuntaje;
    }
}
