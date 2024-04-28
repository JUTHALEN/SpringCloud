package com.msvc.user.external.service;

import com.msvc.user.dto.Qualification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "QUALIFICATION-SERVICE")
public interface QualificationService {
    
    @PostMapping("/api/qualification/{id}")
    ResponseEntity<Qualification> saveQualification(Qualification qualification);
    
    @PostMapping("/api/qualification/{id}")
    ResponseEntity<Qualification> updateQualification(@PathVariable String id, Qualification qualification);
    
    @DeleteMapping("/api/qualification/{id}")
    ResponseEntity<Qualification> deleteQualification(@PathVariable String id);
}
