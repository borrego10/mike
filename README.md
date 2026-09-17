# ⚽ API REST - Selección de Titulares de Fútbol 5

API REST desarrollada con **Java** y **Spring Boot** para resolver el taller técnico de gestión de entrenamientos y selección de los 5 jugadores titulares a partir de métricas de rendimiento semanal.

---

## 📋 Reglas de Negocio

### 1. Ponderación de Métricas por Jugador
En cada sesión de entrenamiento se evalúan 3 variables por cada jugador:
- **Potencia de tiro ($Km/h$):** 20% (`0.20`)
- **Velocidad ($Km/h$):** 30% (`0.30`)
- **Pases efectivos (cantidad):** 50% (`0.50`)

$$\text{Puntaje} = (\text{Potencia} \times 0.20) + (\text{Velocidad} \times 0.30) + (\text{Pases} \times 0.50)$$

#### Ejemplo oficial de la prueba (Entrenamiento #1):
| Jugador | Potencia Tiro | Velocidad | Pases | Puntaje Calculado |
| :--- | :---: | :---: | :---: | :---: |
| **Jugador1** | 10 | 5 | 25 | **16.0** |
| **Jugador2** | 16 | 5 | 20 | **14.7** |
| **Jugador3** | 15 | 3 | 30 | **18.9** |
| **Jugador4** | 12 | 4 | 18 | **12.6** |
| **Jugador5** | 11 | 3 | 19 | **12.6** |
| **Jugador6** | 9  | 3 | 22 | **13.7** |
| **Jugador7** | 10 | 2 | 24 | **14.6** |

### 2. Regla de los 3 Entrenamientos
- Durante la semana se realizan **3 entrenamientos**.
- El algoritmo **únicamente retornará los titulares si se realizaron los 3 entrenamientos**. En caso contrario, retorna un mensaje indicando que no hay suficiente información.
- Se seleccionan los **5 jugadores con mayor promedio acumulado**, ordenados de mayor a menor.

---

## 🏛️ Estructura del Código

El proyecto tiene una estructura limpia y fácil de entender:

```
src/main/java/com/futbol/equipo
├── Futbol5Application.java   # Clase principal para arrancar la aplicación
├── controller/
│   └── FutbolController.java # Expone los 2 endpoints requeridos
├── service/
│   └── FutbolService.java    # Lógica de cálculo de puntajes, promedios y titulares
└── model/
    ├── Jugador.java          # Datos del jugador y cálculo de su nota
    ├── Entrenamiento.java    # Datos del entrenamiento (número, fecha, jugadores)
    └── TitularDTO.java       # Formato de salida del jugador titular
```

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Iniciar la aplicación
En una terminal en la carpeta del proyecto:

```powershell
# En Windows:
.\mvnw.cmd spring-boot:run

# En Linux / Mac:
./mvnw spring-boot:run
```

La API quedará escuchando en `http://localhost:8080`.

### 2. Ejecutar las Pruebas Unitarias (Bonus)
```powershell
.\mvnw.cmd test
```

---

## 📡 Endpoints de la API

### 1. Guardar Entrenamiento
- **Método:** `POST`
- **URL:** `http://localhost:8080/api/entrenamientos`
- **Body (JSON):**
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

### 2. Obtener Equipo Titular
- **Método:** `GET`
- **URL:** `http://localhost:8080/api/titulares`

**Respuesta si hay menos de 3 entrenamientos:**
```json
{
  "mensaje": "No hay suficiente información. Se requieren 3 entrenamientos de la semana.",
  "entrenamientosRegistrados": 1,
  "titulares": []
}
```

**Respuesta cuando se completaron los 3 entrenamientos:**
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
