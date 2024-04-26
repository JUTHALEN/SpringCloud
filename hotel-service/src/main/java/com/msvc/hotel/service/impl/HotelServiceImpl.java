package com.msvc.hotel.service.impl;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msvc.hotel.entity.Hotel;
import com.msvc.hotel.exception.ResourceNotFoundException;
import com.msvc.hotel.repository.HotelRepository;
import com.msvc.hotel.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getById(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel Not Found with id: " + id));
    }
}
