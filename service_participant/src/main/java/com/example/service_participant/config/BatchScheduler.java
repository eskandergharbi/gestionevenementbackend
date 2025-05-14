package com.example.service_participant.config;

//src/main/java/com/example/service_participant/batch/BatchScheduler.java

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

 private final JobLauncher jobLauncher;
 private final Job updateParticipantCountJob;

 @Scheduled(cron = "0 * * * * *") // toutes les heures
 public void runJob() throws Exception {
     JobParameters params = new JobParametersBuilder()
             .addLong("timestamp", System.currentTimeMillis())
             .toJobParameters();

     jobLauncher.run(updateParticipantCountJob, params);
 }

}

