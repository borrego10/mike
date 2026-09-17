package com.futbol.equipo.controller;

import com.futbol.equipo.model.Entrenamiento;
import com.futbol.equipo.service.FutbolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FutbolController {

    private final FutbolService futbolService;

    public FutbolController(FutbolService futbolService) {
        this.futbolService = futbolService;
    }

    // guardar un entrenamiento
    @PostMapping("/entrenamientos")
    public ResponseEntity<Entrenamiento> registrarEntrenamiento(@RequestBody Entrenamiento entrenamiento) {
        Entrenamiento guardado = futbolService.guardarEntrenamiento(entrenamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // obtener los titulares
    @GetMapping("/titulares")
    public ResponseEntity<Map<String, Object>> obtenerTitulares() {
        Map<String, Object> resultado = futbolService.obtenerTitulares();
        return ResponseEntity.ok(resultado);
    }
}
