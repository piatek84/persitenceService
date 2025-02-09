package com.coronel.persistenceService.repository;

import com.coronel.persistenceService.model.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ResultRepository extends MongoRepository<Result, String> {
    Optional<Result> findByDate(LocalDate date);
}
