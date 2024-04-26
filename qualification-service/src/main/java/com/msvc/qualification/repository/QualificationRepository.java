package com.msvc.qualification.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msvc.qualification.entity.Qualification;

public interface QualificationRepository extends MongoRepository<Qualification, String>{

    List<Qualification> findByUserId(String userId);

    List<Qualification> findByHotelId(String hotelId);

}
