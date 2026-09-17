package com.futbol.equipo.controller;

import com.futbol.equipo.model.Entrenamiento;
import com.futbol.equipo.service.FutbolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FutbolController {

    private final FutbolService futbolService;

    public FutbolController(FutbolService futbolService) {
        this.futbolService = futbolService;
    }

    /**
     * Endpoint 1 requerido: Almacenar la información de cada entrenamiento.
     */
    @PostMapping("/entrenamientos")
    public ResponseEntity<Entrenamiento> registrarEntrenamiento(@RequestBody Entrenamiento entrenamiento) {
        Entrenamiento guardado = futbolService.guardarEntrenamiento(entrenamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    /**
     * Endpoint 2 requerido: Obtener el listado de jugadores titulares.
     */
    @GetMapping("/titulares")
    public ResponseEntity<Map<String, Object>> obtenerTitulares() {
        Map<String, Object> resultado = futbolService.obtenerTitulares();
        return ResponseEntity.ok(resultado);
    }

    /**
     * Endpoint auxiliar: Consultar todos los entrenamientos registrados.
     */
    @GetMapping("/entrenamientos")
    public ResponseEntity<List<Entrenamiento>> listarEntrenamientos() {
        return ResponseEntity.ok(futbolService.listarEntrenamientos());
    }

    /**
     * Endpoint auxiliar: Limpiar los entrenamientos para reiniciar la semana.
     */
    @DeleteMapping("/entrenamientos")
    public ResponseEntity<String> reiniciar() {
        futbolService.reiniciar();
        return ResponseEntity.ok("Entrenamientos reiniciados correctamente.");
    }
}
