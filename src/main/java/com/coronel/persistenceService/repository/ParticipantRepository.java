package com.coronel.persistenceService.repository;

import com.coronel.persistenceService.model.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ParticipantRepository extends MongoRepository<Participant, String> {
    Optional<Participant> findByName(String name);
}
