package com.co.sofka.biblioteca.mappers;

import com.co.sofka.biblioteca.collections.Recurso;
import com.co.sofka.biblioteca.dtos.RecursoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoMapper {

    public RecursoDTO toRecursoDTO(Recurso recurso){
        RecursoDTO recursoDTO = new RecursoDTO();
        recursoDTO.setId(recurso.getId());
        recursoDTO.setDisponibilidad(recurso.getEstaDisponible());
        recursoDTO.setFechaPrestamo(recurso.getFechaPrestamo());
        recursoDTO.setTematica(recurso.getTematica());
        recursoDTO.setTipo(recurso.getTipo());
        return recursoDTO;
    }

    public List<RecursoDTO> toRecursoDTO(List<Recurso> recursos){
        return recursos.stream().map(this::toRecursoDTO).collect(Collectors.toList());
    }

    public Recurso toRecurso(RecursoDTO recursodto){
        Recurso recurso = new Recurso();
        recurso.setId(recursodto.getId());
        recurso.setEstaDisponible(recursodto.getDisponibilidad());
        recurso.setFechaPrestamo(recursodto.getFechaPrestamo());
        recurso.setTematica(recursodto.getTematica());
        recurso.setTipo(recursodto.getTipo());
        return recurso;
    }

    public List<Recurso> toRecurso(List<RecursoDTO> recursosDTO){
        return recursosDTO.stream().map(this::toRecurso).collect(Collectors.toList());
    }
}
