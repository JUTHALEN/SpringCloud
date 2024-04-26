package com.msvc.qualification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msvc.qualification.entity.Qualification;
import com.msvc.qualification.repository.QualificationRepository;
import com.msvc.qualification.service.QualificationService;

@Service
public class QualificationServiceImpl implements QualificationService {

    @Autowired
    QualificationRepository qualificationRepository;

    @Override
    public Qualification create(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    @Override
    public List<Qualification> getAllQualifications() {
        return qualificationRepository.findAll();
    }

    @Override
    public List<Qualification> getQualificationsByUserId(String userId) {
        return qualificationRepository.findByUserId(userId);
    }

    @Override
    public List<Qualification> getQualificationsByHotelId(String hotelId) {
        return qualificationRepository.findByHotelId(hotelId);
    }
}
