package com.trainday.microServiceAssistantMedic.Domain.Repository;

import com.trainday.microServiceAssistantMedic.Domain.Model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByCrm(String crm);
}
