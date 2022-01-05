package com.co.sofka.biblioteca.controllers;

import com.co.sofka.biblioteca.dtos.RecursoDTO;
import com.co.sofka.biblioteca.services.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/recurso")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @GetMapping("/todos")
    public ResponseEntity<List<RecursoDTO>> obtenerTodos(){
        return new ResponseEntity<>(recursoService.obtenerTodosRecursos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> obtenerPorId(@PathVariable("id")String id){
        return new ResponseEntity<>(recursoService.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecursoDTO> crearRecurso(@RequestBody RecursoDTO recursoDTO){
        return new ResponseEntity<>(recursoService.crearRecurso(recursoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO> actualizarRecurso(@PathVariable("id") String id, RecursoDTO recursoDTO){
        recursoDTO.setId(id);
        return new ResponseEntity<>(recursoService.actualizarRecurso(recursoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarRecurso(@PathVariable("id") String id){
        recursoService.eliminarRecurso(id);
    }

    @PutMapping("prestar/{id}")
    public ResponseEntity<String> prestar(@PathVariable("id") String id){
        return new ResponseEntity<>(recursoService.prestar(id), HttpStatus.OK);
    }

    @PutMapping("devolver/{id}")
    public ResponseEntity<String> devolver(@PathVariable("id") String id){
        return new ResponseEntity<>(recursoService.devolver(id), HttpStatus.OK);
    }

    @GetMapping("todos/recomendacion")
    public ResponseEntity<List<RecursoDTO>> obtenerPorTipoYTematica
            (@RequestParam Optional<String> tipo, @RequestParam Optional<String> tematica){

        return new ResponseEntity<>(recursoService.obtenerPorTipoYTematica(tipo, tematica), HttpStatus.OK);
    }
}
