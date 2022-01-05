package com.co.sofka.biblioteca.controllers;

import com.co.sofka.biblioteca.collections.Recurso;
import com.co.sofka.biblioteca.dtos.RecursoDTO;
import com.co.sofka.biblioteca.mappers.RecursoMapper;
import com.co.sofka.biblioteca.repositories.RecursoRepository;
import com.co.sofka.biblioteca.services.RecursoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecursoControllerTest {

    @MockBean
    private RecursoService recursoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecursoMapper recursoMapper;

    @Test
    void obtenerTodos() throws Exception {
        List<RecursoDTO> recursosResultDTO = getRecursosDTO();

        doReturn(recursosResultDTO).when(recursoService).obtenerTodosRecursos();

        mockMvc.perform(get("/api/v1/recurso/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));

    }

    @Test
    void obtenerPorId() throws Exception {
        String id = "1";
        RecursoDTO recursoDTO = new RecursoDTO(id, "libro", "carros", LocalDateTime.now(), true);

        doReturn(recursoDTO).when(recursoService).obtenerPorId(id);

        mockMvc.perform(get("/api/v1/recurso/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.tipo", is("libro")))
                .andExpect(jsonPath("$.tematica", is("carros")))
                .andExpect(jsonPath("$.disponibilidad", is(true)));
    }

    List<RecursoDTO> getRecursosDTO() {
        List<RecursoDTO> recursos = new ArrayList<>();
        recursos.add(new RecursoDTO("1", "libro", "carros", LocalDateTime.now(), true));
        recursos.add(new RecursoDTO("2", "libro", "carros", LocalDateTime.now(), true));
        recursos.add(new RecursoDTO("3", "revista", "motos", LocalDateTime.now(), true));
        recursos.add(new RecursoDTO("4", "articulo", "programación", LocalDateTime.now(), false));
        recursos.add(new RecursoDTO("5", "libro", "carros", LocalDateTime.now(), true));

        return recursos;
    }
}