package com.example.servicehealthtracker;

import com.example.servicehealthtracker.entities.Service;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class ServiceHealthTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHealthTrackerApplication.class, args);
    }

    @Bean
    public RestTemplate httpClient() {
        var clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(5000);
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    protected Step healthTrackStep(StepBuilderFactory steps,
                                   ItemReader<Service> reader,
                                   ItemProcessor<Service, Service> processor,
                                   ItemWriter<Service> writer) {
        return steps.get("health-track-step")
                .<Service, Service>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job healthTrackerJob(JobBuilderFactory jobBuilderFactory, Step healthTrackStep) {
        return jobBuilderFactory.get("health-track-job")
                .flow(healthTrackStep)
                .end()
                .build();
    }

}
