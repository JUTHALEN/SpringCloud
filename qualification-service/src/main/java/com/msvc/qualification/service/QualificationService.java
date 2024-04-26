package com.msvc.qualification.service;

import java.util.List;

import com.msvc.qualification.entity.Qualification;

public interface QualificationService {

    Qualification create(Qualification qualification);

    List<Qualification> getAllQualifications();

    List<Qualification> getQualificationsByUserId(String userId);

    List<Qualification> getQualificationsByHotelId(String hotelId);
}
