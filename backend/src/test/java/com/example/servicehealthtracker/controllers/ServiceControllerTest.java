package com.example.servicehealthtracker.controllers;

import com.example.servicehealthtracker.SystemIntegrationTest;
import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.repositories.ServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ServiceControllerTest extends SystemIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ServiceRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    private Service service;

    @BeforeEach
    void setUp() {
        service = repository.save(new Service().withDescription("Test"));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getServices_Success() throws Exception {
        mvc.perform(get("/api/v1/services")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is("Test")));
    }

    @Test
    void getService_Success() throws Exception {
        mvc.perform(get("/api/v1/services/" + service.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Test")));
    }

    @Test
    void createService_Success() throws Exception {
        mvc.perform(post("/api/v1/services")
                .content(objectMapper.writeValueAsBytes(new Service().withDescription("boo")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("boo")))
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    void updateService_Success() throws Exception {
        mvc.perform(put("/api/v1/services/" + service.getId())
                .content(objectMapper.writeValueAsBytes(service.withDescription("foo")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("foo")))
                .andExpect(jsonPath("$.id", is(service.getId().intValue())));
    }

    @Test
    void deleteService_Success() throws Exception {
        mvc.perform(delete("/api/v1/services/" + service.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/services/" + service.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}