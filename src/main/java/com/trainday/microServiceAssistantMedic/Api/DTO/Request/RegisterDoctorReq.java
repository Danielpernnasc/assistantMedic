package com.trainday.microServiceAssistantMedic.Api.DTO.Request;

import com.trainday.microServiceAssistantMedic.Domain.Enum.Role;

public record RegisterDoctorReq (
        String crm,
        String name,
        String email,
        Role role,
        String password
) {

}
