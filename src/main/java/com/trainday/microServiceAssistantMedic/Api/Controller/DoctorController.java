package com.trainday.microServiceAssistantMedic.Api.Controller;

import com.trainday.microServiceAssistantMedic.Api.DTO.Request.DoctorRequest;
import com.trainday.microServiceAssistantMedic.Aplication.Service.DoctorService;
import com.trainday.microServiceAssistantMedic.Api.DTO.response.DoctorResponse;
import com.trainday.microServiceAssistantMedic.Domain.Model.Doctor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/{crm}")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createDoctor(
            @PathVariable("crm") String crm,
            @RequestBody DoctorRequest request) {
        return doctorService.creatDoctor(crm, request);
    }


    @GetMapping("/searchdoctor/{crm}")
    public DoctorResponse getDoctorByCrm(
            @Parameter(description = "CRM do médico a ser buscado", required = true, example = "123456")
            @PathVariable("crm") String crm) {
        return doctorService.searchDoctor(crm);
    }


    @GetMapping("/ListDoctor")
    public List<DoctorResponse> getAllDoctors() {
        return  doctorService.showAllDoctors();
    }
}
