package com.co.sofka.biblioteca.repositories;

import com.co.sofka.biblioteca.collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecursoRepository extends MongoRepository<Recurso, String> {
}
