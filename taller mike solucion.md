# Solucion del taller Futbol 5

Esta solucion se hizo con Spring Boot y Java.

La idea principal es registrar 3 entrenamientos de la semana y despues escoger los 5 mejores jugadores.

## Regla del puntaje

Cada jugador tiene 3 datos:

- Potencia de tiro
- Velocidad
- Pases efectivos

La nota se calcula asi:

```txt
puntaje = (potenciaTiro * 0.20) + (velocidad * 0.30) + (pasesEfectivos * 0.50)
```

## Regla de los 3 entrenamientos

Si todavia no hay 3 entrenamientos, el sistema no selecciona titulares.

Cuando ya existen 3 entrenamientos, el sistema calcula el promedio de cada jugador y escoge los 5 mejores.

## Archivos importantes

- `FutbolController.java`: recibe las peticiones.
- `FutbolService.java`: tiene la logica principal.
- `Jugador.java`: guarda los datos del jugador y calcula el puntaje.
- `Entrenamiento.java`: guarda el numero, fecha y jugadores del entrenamiento.
- `TitularDTO.java`: es la respuesta que se muestra al consultar titulares.

## Endpoints

```txt
POST /api/entrenamientos
GET /api/titulares
```

## Explicacion corta

El usuario registra entrenamientos. Cada entrenamiento trae una lista de jugadores. A cada jugador se le calcula una nota. Cuando se consulta el equipo titular, el programa revisa que existan 3 entrenamientos, calcula el promedio de cada jugador y muestra los 5 mejores.
