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

### Consultar titulares

```txt
GET /api/titulares
```

Si hay menos de 3 entrenamientos, responde que no hay informacion suficiente.

Si ya hay 3 entrenamientos, devuelve los 5 jugadores con mejor promedio.

## Como probar

```powershell
.\mvnw.cmd test
```

## Como explicarlo

1. El controlador recibe las peticiones de la API.
2. El servicio guarda los entrenamientos en una lista.
3. Cada jugador calcula su puntaje con la formula del taller.
4. Cuando se consultan los titulares, primero se revisa que existan 3 entrenamientos.
5. Luego se agrupan los puntajes por nombre de jugador.
6. Se saca el promedio de cada jugador.
7. Se ordenan de mayor a menor.
8. Se retornan los primeros 5 jugadores.

## Nota

El proyecto no usa base de datos. Los datos quedan guardados en memoria mientras la aplicacion esta encendida. Esto se hizo para mantener la solucion simple y facil de explicar.
