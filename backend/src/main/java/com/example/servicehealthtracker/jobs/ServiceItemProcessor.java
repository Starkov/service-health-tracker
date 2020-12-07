package com.example.servicehealthtracker.jobs;

import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.entities.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceItemProcessor implements ItemProcessor<Service, Service> {
    private final RestTemplate httpClient;

    @Override
    public Service process(Service item) {
        var status = checkHealth(item);
        return item.withStatus(status);
    }

    private Status checkHealth(Service item) {
        try {
            var response = httpClient.getForEntity(item.getUrl(), String.class);
            return response.getStatusCode() == HttpStatus.OK ? Status.OK : Status.FAIL;
        } catch (Exception ex) {
            log.error("Something went wrong with this service: {} ", item, ex);
            return Status.FAIL;
        }
    }
}
