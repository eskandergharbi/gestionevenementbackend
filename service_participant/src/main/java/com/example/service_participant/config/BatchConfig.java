package com.example.service_participant.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import com.example.service_participant.dto.EventCountDTO;
import com.example.service_participant.model.Registration;
import com.example.service_participant.repository.RegistrationRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private RegistrationRepository registrationRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Bean
    public Job updateParticipantCountJob(JobRepository jobRepository, Step updateParticipantCountStep) {
        return new JobBuilder("updateParticipantCountJob", jobRepository)
                .start(updateParticipantCountStep)
                .build();
    }

    @Bean
    public Step updateParticipantCountStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("updateParticipantCountStep", jobRepository)
                .<EventCountDTO, EventCountDTO>chunk(10, transactionManager)
                .reader(eventCountReader())
                .processor(eventCountProcessor())
                .writer(eventCountWriter())
                .build();
    }

    @Bean
    public ItemReader<EventCountDTO> eventCountReader() {
        // Récupérer toutes les inscriptions et compter le nombre de participants par événement
        List<Registration> registrations = registrationRepository.findAll();
        Map<String, Long> countMap = registrations.stream().filter(x->x.getEvent()!=null)
                .collect(Collectors.groupingBy(r -> r.getEvent().getId(), Collectors.counting()));

        // Convertir le résultat en une liste d'EventCountDTO
        List<EventCountDTO> eventCounts = countMap.entrySet().stream()
                .map(entry -> new EventCountDTO(entry.getKey(), entry.getValue().intValue(),100))
                .toList();

        // Retourner la liste d'EventCountDTO en tant que lecteur
        return new ListItemReader<>(eventCounts);
    }

    @Bean
    public ItemProcessor<EventCountDTO, EventCountDTO> eventCountProcessor() {
        return item -> {
            // Log de l'événement traité
            log.info("Processing event update for eventId={}, count={}", item.getEventId(), item.getParticipantCount());
            return item;  // Aucun changement nécessaire sur l'élément
        };
    }

    @Bean
    public ItemWriter<EventCountDTO> eventCountWriter() {
        return items -> {
            for (EventCountDTO dto : items) {
                try {
                    // Mise à jour du nombre de participants via une requête PUT
                    log.info("Updating participant count for eventId={} to {}", dto.getEventId(), dto.getParticipantCount());
                    restTemplate.put("http://localhost:8081/events/" + dto.getEventId() + "/participantCount",
                            dto.getParticipantCount());
                } catch (RestClientException e) {
                    // Gestion d'erreur en cas d'échec de la requête PUT
                    log.error("Failed to update participant count for eventId={}. Error: {}", dto.getEventId(), e.getMessage());
                }
            }
        };
    }
}
