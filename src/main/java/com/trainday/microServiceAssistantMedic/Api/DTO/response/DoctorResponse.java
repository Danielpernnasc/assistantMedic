package com.trainday.microServiceAssistantMedic.Api.DTO.response;

import com.trainday.microServiceAssistantMedic.Domain.Enum.Role;

public record DoctorResponse(
        String id,
        String name,
        String email,
        String crm,
        Role role
) {
}
