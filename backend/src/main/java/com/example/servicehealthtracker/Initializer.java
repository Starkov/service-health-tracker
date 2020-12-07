package com.example.servicehealthtracker;


import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.entities.Status;
import com.example.servicehealthtracker.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("!test")
@RequiredArgsConstructor
class Initializer implements CommandLineRunner {

    private final ServiceRepository repository;

    @Override
    public void run(String... strings) {

        var googleService = new Service();
        googleService.setUrl("http://googleService.com");
        googleService.setDescription("google");
        googleService.setStatus(Status.FAIL);
        googleService.setCreatedDate(LocalDateTime.now());
        googleService.setUpdatedDate(LocalDateTime.now());

        repository.save(googleService);

        repository.findAll().forEach(System.out::println);
    }
}