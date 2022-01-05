package com.co.sofka.biblioteca.repositories;

import com.co.sofka.biblioteca.collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecursoRepository extends MongoRepository<Recurso, String> {

    List<Recurso> findAllByTipoAndAndTematica(String tipo, String tematica);
}
