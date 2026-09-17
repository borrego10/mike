package com.futbol.equipo.model;

public class Jugador {

    private String nombreJugador;
    private double potenciaTiro;
    private double velocidad;
    private int pasesEfectivos;
    private double puntaje;

    public Jugador() {
    }

    public Jugador(String nombreJugador, double potenciaTiro, double velocidad, int pasesEfectivos) {
        this.nombreJugador = nombreJugador;
        this.potenciaTiro = potenciaTiro;
        this.velocidad = velocidad;
        this.pasesEfectivos = pasesEfectivos;
        this.calcularPuntaje();
    }

    // metodo para calcular el puntaje con las ponderaciones
    public void calcularPuntaje() {
        double calculo = (this.potenciaTiro * 0.20) + (this.velocidad * 0.30) + (this.pasesEfectivos * 0.50);
        this.puntaje = Math.round(calculo * 100.0) / 100.0;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public double getPotenciaTiro() {
        return potenciaTiro;
    }

    public void setPotenciaTiro(double potenciaTiro) {
        this.potenciaTiro = potenciaTiro;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public int getPasesEfectivos() {
        return pasesEfectivos;
    }

    public void setPasesEfectivos(int pasesEfectivos) {
        this.pasesEfectivos = pasesEfectivos;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
}
