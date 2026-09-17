# Taller Fútbol 5 - Selección de Titulares

Proyecto en Spring Boot (Java) para registrar los entrenamientos semanales de un equipo de fútbol 5 y calcular los 5 jugadores titulares de acuerdo a su rendimiento.

## Reglas del Algoritmo
- **Cálculo de puntaje por entrenamiento:**
  `Puntaje = (Potencia * 0.20) + (Velocidad * 0.30) + (Pases * 0.50)`
- **Condición de los 3 entrenamientos:**
  Solo se puede calcular el equipo titular si se han completado los 3 entrenamientos semanales. Si hay menos, la API devuelve un mensaje informando que no hay suficiente información.
- **Titulares:**
  Se seleccionan los 5 jugadores con mejor promedio en la semana ordenados de mayor a menor puntaje.

## Cómo ejecutar el proyecto

### 1. Iniciar el servidor
```powershell
.\mvnw.cmd spring-boot:run
```
La aplicación inicia en `http://localhost:8080`.

### 2. Correr las pruebas unitarias
```powershell
.\mvnw.cmd test
```

## Endpoints

### 1. Registrar entrenamiento
- **POST** `/api/entrenamientos`
- **Body:**
```json
{
  "numeroEntrenamiento": 1,
  "fecha": "2026-09-17",
  "jugadores": [
    { "nombreJugador": "Jugador1", "potenciaTiro": 10.0, "velocidad": 5.0, "pasesEfectivos": 25 },
    { "nombreJugador": "Jugador2", "potenciaTiro": 16.0, "velocidad": 5.0, "pasesEfectivos": 20 },
    { "nombreJugador": "Jugador3", "potenciaTiro": 15.0, "velocidad": 3.0, "pasesEfectivos": 30 },
    { "nombreJugador": "Jugador4", "potenciaTiro": 12.0, "velocidad": 4.0, "pasesEfectivos": 18 },
    { "nombreJugador": "Jugador5", "potenciaTiro": 11.0, "velocidad": 3.0, "pasesEfectivos": 19 },
    { "nombreJugador": "Jugador6", "potenciaTiro": 9.0,  "velocidad": 3.0, "pasesEfectivos": 22 },
    { "nombreJugador": "Jugador7", "potenciaTiro": 10.0, "velocidad": 2.0, "pasesEfectivos": 24 }
  ]
}
```

### 2. Obtener titulares
- **GET** `/api/titulares`

**Si faltan entrenamientos (menos de 3):**
```json
{
  "mensaje": "No hay suficiente información. Se requieren 3 entrenamientos de la semana.",
  "entrenamientosRegistrados": 1,
  "titulares": []
}
```

**Si se completaron los 3 entrenamientos:**
```json
{
  "mensaje": "Equipo titular determinado exitosamente.",
  "totalEntrenamientos": 3,
  "titulares": [
    { "posicion": 1, "nombreJugador": "Jugador3", "promedioPuntaje": 18.9 },
    { "posicion": 2, "nombreJugador": "Jugador1", "promedioPuntaje": 16.0 },
    { "posicion": 3, "nombreJugador": "Jugador2", "promedioPuntaje": 14.7 },
    { "posicion": 4, "nombreJugador": "Jugador7", "promedioPuntaje": 14.6 },
    { "posicion": 5, "nombreJugador": "Jugador6", "promedioPuntaje": 13.7 }
  ]
}
```
