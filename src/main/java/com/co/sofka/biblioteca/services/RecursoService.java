package com.co.sofka.biblioteca.services;

import com.co.sofka.biblioteca.collections.Recurso;
import com.co.sofka.biblioteca.dtos.RecursoDTO;
import com.co.sofka.biblioteca.mappers.RecursoMapper;
import com.co.sofka.biblioteca.repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecursoService {

    private static final String RECURSO_NO_ENCONTRADO = "No se encuentra un recurso con el Id ingresado";
    @Autowired
    private RecursoRepository repository;

    @Autowired
    private RecursoMapper recursoMapper;

    public List<RecursoDTO> obtenerTodosRecursos(){
        List<Recurso> recursos = repository.findAll();

        return recursoMapper.toRecursoDTO(recursos);
    }

    public RecursoDTO obtenerPorId(String id){
        Recurso recurso = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(RECURSO_NO_ENCONTRADO)
        );

        return recursoMapper.toRecursoDTO(recurso);
    }

    public RecursoDTO crearRecurso(RecursoDTO recursoDTO){
        Recurso recurso = recursoMapper.toRecurso(recursoDTO);

        Recurso recursoCreado = repository.save(recurso);

        return recursoMapper.toRecursoDTO(recursoCreado);
    }

    public RecursoDTO actualizarRecurso(RecursoDTO recursoDTO){
        if(!repository.existsById(recursoDTO.getId())){
            throw new IllegalArgumentException(RECURSO_NO_ENCONTRADO);
        }

        Recurso recurso = recursoMapper.toRecurso(recursoDTO);

        Recurso recursoActualizado = repository.save(recurso);

        return recursoMapper.toRecursoDTO(recursoActualizado);
    }

    public void eliminarRecurso(String id){
        if(!repository.existsById(id)){
            throw new IllegalArgumentException(RECURSO_NO_ENCONTRADO);
        }

        repository.deleteById(id);
    }
}
