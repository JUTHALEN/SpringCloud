package com.msvc.hotel.service;

import java.util.List;

import com.msvc.hotel.entity.Hotel;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel getById(String id);
}
