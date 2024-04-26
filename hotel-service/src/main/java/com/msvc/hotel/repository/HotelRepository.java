package com.msvc.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msvc.hotel.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
