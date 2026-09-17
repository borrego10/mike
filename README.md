# Futbol 5

Este proyecto es una API sencilla hecha con Java y Spring Boot.

Sirve para registrar entrenamientos de un equipo de futbol 5 y despues sacar los 5 titulares segun el puntaje de cada jugador.

## Que hace

- Guarda entrenamientos.
- Calcula la nota de los jugadores.
- Revisa que ya existan 3 entrenamientos.
- Saca el promedio de cada jugador.
- Muestra los 5 mejores.

## Formula

La formula que se usa es:

```txt
puntaje = (potenciaTiro * 0.20) + (velocidad * 0.30) + (pasesEfectivos * 0.50)
```

O sea:

- potencia de tiro vale 20%
- velocidad vale 30%
- pases efectivos vale 50%

## Ejecutar

```powershell
.\mvnw.cmd spring-boot:run
```

La API queda en:

```txt
http://localhost:8080
```

## Registrar entrenamiento

Metodo:

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

JSON es el formato que se manda al servidor. En este caso trae el numero del entrenamiento, la fecha y los jugadores.

## Consultar titulares

Metodo:

```txt
GET /api/titulares
```

Si faltan entrenamientos responde algo asi:

```json
{
  "mensaje": "No hay suficiente informacion. Se requieren 3 entrenamientos de la semana.",
  "entrenamientosRegistrados": 1,
  "titulares": []
}
```

Si ya hay 3 entrenamientos responde con los titulares:

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

## Pruebas

```powershell
.\mvnw.cmd test
```

## Explicacion corta

El controlador recibe el JSON. Spring lo convierte en un objeto de Java. Luego el servicio guarda el entrenamiento en una lista y calcula la nota de cada jugador.

Cuando se piden los titulares, primero se valida que existan 3 entrenamientos. Despues se suman los puntajes por jugador, se calcula el promedio, se ordenan de mayor a menor y se devuelven los primeros 5.

No use base de datos para que el proyecto quedara mas simple. Los datos se guardan en memoria mientras la aplicacion esta prendida.
