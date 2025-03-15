package com.coronel.persistenceService.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "participants")
public class Participant {

    @Id
    ObjectId id;


    String name;

    String country;

    Type type;

    String urlIcon;
}



