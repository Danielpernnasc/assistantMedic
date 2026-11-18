package com.trainday.microServiceAssistantMedic.Domain.Repository;

import com.trainday.microServiceAssistantMedic.Domain.Model.LoginDoctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LoginDoctorRepository extends MongoRepository<LoginDoctor, String> {
    Optional<LoginDoctor> findByCrm(String crm);

    Optional<LoginDoctor> findByEmail(String email);

    boolean existsByCrm(String crn);

    boolean existsByEmail(String email);
}
