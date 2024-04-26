package com.msvc.qualification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("qualifications")
public class Qualification {

    @Id
    String id;

    String userId;

    String hotelId;

    int qualification;

    String observation;
}
