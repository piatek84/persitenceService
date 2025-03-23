package com.coronel.persistenceService.model;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enabler {
    boolean isEnabled;
}



