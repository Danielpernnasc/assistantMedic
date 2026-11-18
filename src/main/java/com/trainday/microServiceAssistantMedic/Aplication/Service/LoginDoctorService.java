package com.trainday.microServiceAssistantMedic.Aplication.Service;

import com.trainday.microServiceAssistantMedic.Api.DTO.Request.LoginRequest;
import com.trainday.microServiceAssistantMedic.Api.DTO.Request.RegisterDoctorReq;
import com.trainday.microServiceAssistantMedic.Api.DTO.response.LoginDoctorResponse;
import com.trainday.microServiceAssistantMedic.Domain.Enum.Role;
import com.trainday.microServiceAssistantMedic.Domain.Model.Doctor;
import com.trainday.microServiceAssistantMedic.Domain.Model.LoginDoctor;
import com.trainday.microServiceAssistantMedic.Domain.Repository.DoctorRepository;
import com.trainday.microServiceAssistantMedic.Domain.Repository.LoginDoctorRepository;
import com.trainday.microServiceAssistantMedic.infra.Service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginDoctorService {

    private final LoginDoctorRepository loginDoctorRepository;
    private final DoctorRepository doctorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public LoginDoctorService(
            LoginDoctorRepository loginDoctorRepository,
            DoctorRepository doctorRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.loginDoctorRepository = loginDoctorRepository;
        this.doctorRepository = doctorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

 public LoginDoctorResponse createLogin(RegisterDoctorReq request){
        if(loginDoctorRepository.existsByCrm(request.crm()) || loginDoctorRepository.existsByEmail(request.email())){
            throw new RuntimeException("LoginDoctor with this CRM or Email already exists");
        }

     LoginDoctor login = new LoginDoctor();
        login.setEmail(request.email());
        login.setName(request.name());
        login.setCrm(request.crm());
        login.setRole(request.role());
        login.setPassword(request.password());
        login.setPassword(passwordEncoder.encode(request.password()));

        LoginDoctor saved = loginDoctorRepository.save(login);

        return new LoginDoctorResponse(
                saved.getId(),
                request.name(),
                saved.getEmail(),
                saved.getCrm(),
                saved.getRole()
        );
 }

 public String authenticate(LoginRequest request){
        Optional<LoginDoctor> user = loginDoctorRepository.findByEmail(request.login());

        if(user.isEmpty()){
            user = loginDoctorRepository.findByCrm(request.login());
        }

        LoginDoctor login = user.orElseThrow(() ->
            new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario não encontrado"));

            if(!passwordEncoder.matches(request.password(), login.getPassword())){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha incorreta");
            }

            Optional<Doctor> doctor = doctorRepository.findByCrm(login.getCrm());

            Role role = doctor
                    .map(Doctor::getRole)
                    .orElse(Role.DOCTOR);
            return jwtService.generateToken(
                    login.getEmail(),
                    login.getCrm(),
                    login.getId(),
                    role);


 }


}
