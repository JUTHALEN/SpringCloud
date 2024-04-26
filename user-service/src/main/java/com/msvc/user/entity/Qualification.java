package com.msvc.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qualification {

    String id;

    String userId;

    String hotelId;

    int qualification;

    String observation;
}
