package com.example.servicehealthtracker.jobs;

import com.example.servicehealthtracker.entities.Service;
import com.example.servicehealthtracker.entities.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceItemProcessorTest {

    @Mock
    private RestTemplate httpClient;
    @InjectMocks
    private ServiceItemProcessor serviceItemProcessor;

    @Test
    void shouldReturnOkWhenUrlIsCorrect() {
        var service = new Service().withUrl("http://www.google.com");

        when(httpClient.getForEntity(eq(service.getUrl()), eq(String.class)))
                .thenReturn(ResponseEntity.ok("I am well!"));

        var result = serviceItemProcessor.process(service);

        assertThat(result.getStatus(), equalTo(Status.OK));
    }

    @Test
    void shouldReturnFailWhenUrlIsNotCorrect() {
        var service = new Service().withUrl("http:/bob,bob,boo");

        when(httpClient.getForEntity(eq(service.getUrl()), eq(String.class)))
                .thenReturn(ResponseEntity.notFound().build());

        var result = serviceItemProcessor.process(service);

        assertThat(result.getStatus(), equalTo(Status.FAIL));
    }

    @Test
    void shouldReturnFailWhenUrlIsNull() {
        var service = new Service().withUrl(null);

        when(httpClient.getForEntity(eq(service.getUrl()), eq(String.class)))
                .thenReturn(ResponseEntity.notFound().build());

        var result = serviceItemProcessor.process(service);

        assertThat(result.getStatus(), equalTo(Status.FAIL));
    }

    @Test
    void shouldReturnFailWhenExceptionIsThrown() {
        var service = new Service().withUrl(null);

        when(httpClient.getForEntity(eq(service.getUrl()), eq(String.class)))
                .thenThrow(new RuntimeException("Oops..."));

        var result = serviceItemProcessor.process(service);

        assertThat(result.getStatus(), equalTo(Status.FAIL));
    }
}