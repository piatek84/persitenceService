package com.coronel.persistenceService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "result")
public class Result {

    @Id
    ObjectId id;

    ObjectId participantId1;

    ObjectId participantId2;

    String result;

    WINDRAWLOST winDrawLostParticipant1;

    WINDRAWLOST winDrawLostParticipant2;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    LocalDate date;

    String championship;
}



