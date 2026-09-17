# Futbol 5 - Seleccion de titulares

Proyecto sencillo en Java con Spring Boot para registrar entrenamientos de futbol 5 y escoger los 5 jugadores titulares segun su rendimiento.

## Que hace el proyecto

- Registra entrenamientos.
- Calcula el puntaje de cada jugador.
- Valida que existan 3 entrenamientos.
- Calcula el promedio de cada jugador.
- Devuelve los 5 mejores jugadores como titulares.

## Formula usada

```txt
puntaje = (potenciaTiro * 0.20) + (velocidad * 0.30) + (pasesEfectivos * 0.50)
```

Esto significa:

- Potencia de tiro vale 20%.
- Velocidad vale 30%.
- Pases efectivos vale 50%.

## Como ejecutar

En la carpeta del proyecto:

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicacion queda en:

```txt
http://localhost:8080
```

## Endpoints

La API recibe y responde datos en formato JSON.

JSON es un formato de texto que se usa para enviar datos entre el cliente y el servidor. En este proyecto se usa para mandar los entrenamientos con sus jugadores.

### Registrar entrenamiento

```txt
POST /api/entrenamientos
```

Ejemplo de JSON:

```json
{
  "numeroEntrenamiento": 1,
  "fecha": "2026-09-17",
  "jugadores": [
    {
      "nombreJugador": "Jugador1",
      "potenciaTiro": 10,
      "velocidad": 5,
      "pasesEfectivos": 25
    }
  ]
}
```

En este JSON:

- `numeroEntrenamiento` indica si es el entrenamiento 1, 2 o 3.
- `fecha` guarda el dia del entrenamiento.
- `jugadores` es la lista de jugadores evaluados.
- Cada jugador trae potencia, velocidad y pases.

### Consultar titulares

```txt
GET /api/titulares
```

Si hay menos de 3 entrenamientos, responde que no hay informacion suficiente.

Ejemplo:

```json
{
  "mensaje": "No hay suficiente informacion. Se requieren 3 entrenamientos de la semana.",
  "entrenamientosRegistrados": 1,
  "titulares": []
}
```

Si ya hay 3 entrenamientos, devuelve los 5 jugadores con mejor promedio.

Ejemplo:

```json
{
  "mensaje": "Equipo titular determinado exitosamente.",
  "totalEntrenamientos": 3,
  "titulares": [
    {
      "posicion": 1,
      "nombreJugador": "Jugador3",
      "promedioPuntaje": 18.9
    }
  ]
}
```

## Como probar

```powershell
.\mvnw.cmd test
```

## Como explicarlo

1. El controlador recibe las peticiones de la API.
2. El JSON que llega se convierte en un objeto `Entrenamiento`.
3. El servicio guarda los entrenamientos en una lista.
4. Cada jugador calcula su puntaje con la formula del taller.
5. Cuando se consultan los titulares, primero se revisa que existan 3 entrenamientos.
6. Luego se agrupan los puntajes por nombre de jugador.
7. Se saca el promedio de cada jugador.
8. Se ordenan de mayor a menor.
9. Se retornan los primeros 5 jugadores.

## Nota

El proyecto no usa base de datos. Los datos quedan guardados en memoria mientras la aplicacion esta encendida. Esto se hizo para mantener la solucion simple y facil de explicar.
