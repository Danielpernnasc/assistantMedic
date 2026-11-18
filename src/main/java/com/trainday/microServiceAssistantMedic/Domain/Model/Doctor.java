package com.trainday.microServiceAssistantMedic.Domain.Model;

import com.trainday.microServiceAssistantMedic.Domain.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    private String id;
    private String name;
    private String crm;
    private String email;
    private LocalDate bornDate;
    private String cpf;
    private String phone;
    private String address;
    private Role role;
    private boolean active = true;

}
