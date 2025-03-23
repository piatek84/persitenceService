package com.coronel.persistenceService.consumer;

import com.coronel.persistenceService.model.Deletion;
import com.coronel.persistenceService.model.Enabler;
import com.coronel.persistenceService.model.Participant;
import com.coronel.persistenceService.model.Result;
import com.coronel.persistenceService.repository.ParticipantRepository;
import com.coronel.persistenceService.repository.ResultRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ff4j.FF4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.coronel.persistenceService.config.FF4jConfig.CONSUMER_ENABLED;


@Service
public class KafkaConsumerService {
    private final ParticipantRepository participantRepository;
    private final ResultRepository resultRepository;
    private final FF4j ff4j;

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    //@Autowired This is optional if you have only one constructor
    public KafkaConsumerService(ParticipantRepository participantRepository, ResultRepository resultRepository, FF4j ff4j) {
        this.participantRepository = participantRepository;
        this.resultRepository = resultRepository;
        this.ff4j = ff4j;

    }

    @KafkaListener(topics = "addParticipant", groupId = "cgroup")
    public void addParticipant(String message) throws JsonProcessingException {
        //TODO: Create an api to enable/disable the consumer
        if (ff4j.check(CONSUMER_ENABLED)) {
            Participant participant = new ObjectMapper().readValue(message, Participant.class);
            Participant dataBaseParticipant = participantRepository.save(participant);
            logger.info("**** Participant with name: {} saved with id: {} ****", dataBaseParticipant.getName(), dataBaseParticipant.getId());
        } else logger.info("**** Ignore message because Consumer is not enabled ****");
    }

    @KafkaListener(topics = "deleteParticipant", groupId = "cgroup")
    public void deleteParticipant(String message) throws JsonProcessingException {
        if (ff4j.check(CONSUMER_ENABLED)) {
            Deletion deletion = new ObjectMapper().readValue(message, Deletion.class);
            Optional<Participant> dataBaseParticipant = participantRepository.findById(deletion.getId().toString());
            //TODO: Control that participant can be not found or get can get more than one participant
            participantRepository.deleteById(dataBaseParticipant.get().getId().toString());
            logger.info("**** Participant deleted with name: {} and id: {} ****", dataBaseParticipant.get().getName(), dataBaseParticipant.get().getId());
        } else logger.info("**** Ignore message because Consumer is not enabled ****");
    }

    @KafkaListener(topics = "addResult", groupId = "cgroup")
    public void addResult(String message) throws JsonProcessingException {
        if (ff4j.check(CONSUMER_ENABLED)) {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Result result = objectMapper.readValue(message, Result.class);
            Result dataBaseResult = resultRepository.save(result);
            logger.info("**** Result saved with id: {} ****", dataBaseResult.getId());
        } else logger.info("**** Ignore message because Consumer is not enabled ****");
    }

    @KafkaListener(topics = "deleteResult", groupId = "cgroup")
    public void deleteResult(String message) throws JsonProcessingException {
        if (ff4j.check(CONSUMER_ENABLED)) {
            Deletion deletion = new ObjectMapper().readValue(message, Deletion.class);
            Optional<Result> dataBaseResult = resultRepository.findById(deletion.getId().toString());
            //TODO: Control that result can be not found or get can get more than one result
            resultRepository.deleteById(dataBaseResult.get().getId().toString());
            logger.info("**** Result deleted with id: {} ****", dataBaseResult.get().getId());
        } else logger.info("**** Ignore message because Consumer is not enabled ****");
    }

    @KafkaListener(topics = "enableConsumer", groupId = "cgroup")
    public void enableConsumer(String message) throws JsonProcessingException {
        Enabler enabler = new ObjectMapper().readValue(message, Enabler.class);
        if (enabler.isEnabled()) ff4j.enable(CONSUMER_ENABLED);
        else ff4j.disable(CONSUMER_ENABLED);
        logger.info("**** enableConsumer: {} ****", enabler.isEnabled());
    }
}
