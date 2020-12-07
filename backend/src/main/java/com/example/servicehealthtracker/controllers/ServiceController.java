package com.example.servicehealthtracker.controllers;

import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceRepository serviceRepository;

    @GetMapping("/services")
    public Collection<Service> getServices() {
        log.info("Fetching all services");
        return serviceRepository.findAll();
    }

    @GetMapping("/services/{id}")
    ResponseEntity<?> getService(@PathVariable Long id) {
        log.info("Searching a service by ID: {}", id);
        Optional<Service> service = serviceRepository.findById(id);
        return service.map(it -> ResponseEntity.ok().body(it)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/services")
    ResponseEntity<Service> createService(@Valid @RequestBody Service service) throws URISyntaxException {
        log.info("Saving a service: {}", service);
        Service result = serviceRepository.save(service);
        return ResponseEntity.created(new URI("/api/services/" + result.getId())).body(result);
    }

    @PutMapping("/services/{id}")
    ResponseEntity<Service> updateService(@Valid @RequestBody Service service) {
        log.info("Updating a service: {}", service);
        Service result = serviceRepository.save(service);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        log.info("Deleting a service with ID: {}", id);
        serviceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
