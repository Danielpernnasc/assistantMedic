package com.trainday.microServiceAssistantMedic.Domain.Model;

import com.trainday.microServiceAssistantMedic.Domain.Enum.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "login_doctor")
public class LoginDoctor {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Indexed(unique = true)
    private String crm;
    @Indexed(unique = true)
    private String name;
    private String email;
    private String password;
    @NotNull(message = "Role cannot be null")
    private Role role;
}
