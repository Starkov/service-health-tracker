package com.example.servicehealthtracker.schedulers;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class HealthTrackerBatchScheduler {
    private JobLauncher jobLauncher;
    private Job healthTrackerJob;

    @Scheduled(cron = "0 * * * * *")
    public void schedule() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobInstanceAlreadyCompleteException,
            JobRestartException {

        jobLauncher.run(healthTrackerJob, new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
    }
}
