package com.marfuego.ms_locales.controller;

import com.marfuego.ms_locales.model.Local;
import com.marfuego.ms_locales.model.Ubicacion;
import com.marfuego.ms_locales.service.LocalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locales") // Ruta base
public class LocalController {

    @Autowired
    private LocalService service;

    // 1. Listar todos los registros
    @GetMapping
    public ResponseEntity<List<Local>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 2. Obtener registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Local> obtenerRegistroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 3. Crear nuevo registro (Incluyendo la validacion de los datos ingresados)
    @PostMapping
    public ResponseEntity<Local> crearRegistro(@Valid @RequestBody Local local) {
        Local nuevoLocal = service.guardar(local);
        return new ResponseEntity<>(nuevoLocal, HttpStatus.CREATED); // Retorna estado 201
    }

    // 4. Actualizar registro existente
    @PutMapping("/{id}")
    public ResponseEntity<Local> actualizarRegistro(@PathVariable Long id, @Valid @RequestBody Local localActualizado) {
        return ResponseEntity.ok(service.actualizar(id, localActualizado));
    }

    // 5. Eliminar registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // 6. buscar registro por su ubicacion
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<List<Local>> buscarRegistroPorUbicacion(@PathVariable Ubicacion ubicacion) {
        return ResponseEntity.ok(service.buscarPorUbicacion(ubicacion));
    }

}