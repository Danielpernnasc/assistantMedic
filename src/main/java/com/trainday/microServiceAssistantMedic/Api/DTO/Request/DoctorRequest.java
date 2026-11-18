package com.trainday.microServiceAssistantMedic.Api.DTO.Request;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DoctorRequest(
        @JsonAlias("Name")
        String name,
        String CRM,
        String email,
        String bornDate,
        String cpf,
        String phone,
        String address,
        Boolean active
) {
}
