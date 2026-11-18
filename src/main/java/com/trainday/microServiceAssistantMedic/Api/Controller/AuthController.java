package com.trainday.microServiceAssistantMedic.Api.Controller;


import com.trainday.microServiceAssistantMedic.Api.DTO.Request.LoginRequest;
import com.trainday.microServiceAssistantMedic.Api.DTO.Request.RegisterDoctorReq;
import com.trainday.microServiceAssistantMedic.Api.DTO.response.LoginDoctorResponse;
import com.trainday.microServiceAssistantMedic.Aplication.Service.LoginDoctorService;
import com.trainday.microServiceAssistantMedic.Domain.Model.LoginDoctor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginDoctorService loginService;

    public AuthController(LoginDoctorService loginService){
        this.loginService = loginService;
    }
    @PostMapping("/register")
    public LoginDoctorResponse register(@RequestBody RegisterDoctorReq request) {
        return loginService.createLogin(request);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = loginService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}
