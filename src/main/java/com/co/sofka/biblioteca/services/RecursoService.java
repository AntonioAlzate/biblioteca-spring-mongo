package com.co.sofka.biblioteca.services;

import com.co.sofka.biblioteca.collections.Recurso;
import com.co.sofka.biblioteca.dtos.RecursoDTO;
import com.co.sofka.biblioteca.mappers.RecursoMapper;
import com.co.sofka.biblioteca.repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecursoService {

    private static final String RECURSO_NO_ENCONTRADO = "No se encuentra un recurso con el Id ingresado";
    private static final String RECURSO_YA_PRESTADO = "El recurso se encuentra prestado actualmente";
    private static final String PRESTAMO_COMPLETADO = "Prestamo completado correctamente";
    private static final String RECURSO_NO_PRESTADO = "El recurso no se encuentra prestado, por lo tanto no se puede devolver";
    private static final String RECURSO_DEVUELTO_CORRECTAMENTE = "El recurso se devolvio correctamente";
    @Autowired
    private RecursoRepository repository;

    @Autowired
    private RecursoMapper recursoMapper;

    public List<RecursoDTO> obtenerTodosRecursos() {
        List<Recurso> recursos = repository.findAll();

        return recursoMapper.toRecursoDTO(recursos);
    }

    public RecursoDTO obtenerPorId(String id) {
        Recurso recurso = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(RECURSO_NO_ENCONTRADO)
        );

        return recursoMapper.toRecursoDTO(recurso);
    }

    public RecursoDTO crearRecurso(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.toRecurso(recursoDTO);
        String  id = UUID.randomUUID().toString().substring(0,10);
        recurso.setId(id);

        Recurso recursoCreado = repository.save(recurso);

        return recursoMapper.toRecursoDTO(recursoCreado);
    }

    public RecursoDTO actualizarRecurso(RecursoDTO recursoDTO) {
        if (!repository.existsById(recursoDTO.getId())) {
            throw new IllegalArgumentException(RECURSO_NO_ENCONTRADO);
        }

        Recurso recurso = recursoMapper.toRecurso(recursoDTO);

        Recurso recursoActualizado = repository.save(recurso);

        return recursoMapper.toRecursoDTO(recursoActualizado);
    }

    public void eliminarRecurso(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(RECURSO_NO_ENCONTRADO);
        }

        repository.deleteById(id);
    }

    public String prestar(String id) {

        Recurso recurso = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(RECURSO_NO_ENCONTRADO));

        if(!recurso.getEstaDisponible()){
            return RECURSO_YA_PRESTADO;
        }

        recurso.setEstaDisponible(false);
        recurso.setFechaPrestamo(LocalDateTime.now());

        Recurso recursoActualizado = repository.save(recurso);

        return PRESTAMO_COMPLETADO + " - fecha prestamo: " + recursoActualizado.getFechaPrestamo();
    }


    public String  devolver(String id) {
        Recurso recurso = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(RECURSO_NO_ENCONTRADO));

        if(recurso.getEstaDisponible()){
            return RECURSO_NO_PRESTADO;
        }
        
        recurso.setEstaDisponible(true);
        repository.save(recurso);
        
        return RECURSO_DEVUELTO_CORRECTAMENTE;
    }

    public List<RecursoDTO> obtenerPorTipoYTematica(Optional<String> tipo, Optional<String> tematica) {

        String tipin = tipo.orElse("");
        String  tematiquin = tematica.orElse("");

        List<Recurso> recursosTematicaArea =
                repository.findAllByTipoAndAndTematica(tipin, tematiquin);

        return recursoMapper.toRecursoDTO(recursosTematicaArea);
    }
}
