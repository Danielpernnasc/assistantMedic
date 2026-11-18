package com.trainday.microServiceAssistantMedic.Aplication.Service;

import com.trainday.microServiceAssistantMedic.Api.DTO.Request.DoctorRequest;
import com.trainday.microServiceAssistantMedic.Api.DTO.response.DoctorResponse;
import com.trainday.microServiceAssistantMedic.Domain.Model.Doctor;
import com.trainday.microServiceAssistantMedic.Domain.Model.LoginDoctor;
import com.trainday.microServiceAssistantMedic.Domain.Repository.DoctorRepository;
import com.trainday.microServiceAssistantMedic.Domain.Repository.LoginDoctorRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final LoginDoctorRepository loginDoctorRepository;

    public DoctorService(
            DoctorRepository doctorRepository,
            LoginDoctorRepository loginDoctorRepository) {
        this.doctorRepository = doctorRepository;
        this.loginDoctorRepository = loginDoctorRepository;
    }

    public Doctor creatDoctor(String crm, DoctorRequest request) {
        LoginDoctor user = loginDoctorRepository.findByCrm(crm)
                .orElseThrow(() -> new RuntimeException("LoginDoctor not found"));
        if(doctorRepository.findByCrm(user.getCrm()).isPresent()){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Este login já possui um médico cadastrado");
        }

        Doctor doctor = new Doctor();
        String name = request.name() != null ? request.name() : user.getName();
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do médico é obrigatório");
        }
        doctor.setName(name);
        doctor.setEmail(user.getEmail());
        doctor.setCpf(request.cpf());
        doctor.setCrm(user.getCrm());
        doctor.setAddress(request.address());
        doctor.setRole(user.getRole());
        doctor.setPhone(request.phone());
        doctor.setBornDate(LocalDate.parse(request.bornDate()));
        doctor.setActive(request.active());

        try{
            return doctorRepository.save(doctor);

        }catch (Exception e){
            throw new RuntimeException("Error creating Doctor " + e.getMessage());
        }
    }

    public DoctorResponse searchDoctor(String crm) {
        Doctor doctor = doctorRepository.findByCrm(crm)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return new DoctorResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getCrm(),
                doctor.getRole()
        );
    }

    public List<DoctorResponse> showAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctor -> new DoctorResponse(
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getEmail(),
                        doctor.getCrm(),
                        doctor.getRole()
                ))
                .toList();
    }
}
