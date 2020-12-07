package com.example.servicehealthtracker.jobs;

import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceItemWriter implements ItemWriter<Service> {
    private final ServiceRepository serviceRepository;

    @Override
    public void write(List<? extends Service> items) throws Exception {
        serviceRepository.saveAll(items);
    }
}
