package com.example.servicehealthtracker.jobs;

import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class ServiceItemReader implements ItemReader<Service> {

    private final ServiceRepository serviceRepository;
    private Iterator<Service> serviceIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        serviceIterator = serviceRepository.findAll().iterator();
    }

    @Override
    public Service read() {
        return serviceIterator == null || !serviceIterator.hasNext() ? null : serviceIterator.next();
    }
}
